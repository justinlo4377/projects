.include "./cs47_proj_macro.asm"
.include "./cs47_common_macro.asm"
.text
.globl au_logical
# TBD: Complete your project procedures
# Needed skeleton is given
#####################################################################
# Implement au_logical
# Argument:
# 	$a0: First number
#	$a1: Second number
#	$a2: operation code ('+':add, '-':sub, '*':mul, '/':div)
# Return:
#	$v0: ($a0+$a1) | ($a0-$a1) | ($a0*$a1):LO | ($a0 / $a1)
# 	$v1: ($a0 * $a1):HI | ($a0 % $a1)
# Notes:
#####################################################################
au_logical:
	beq 	$a2, '+', add_logical
	beq	$a2, '*', mult_logical
	beq	$a2, '-', sub_logical
	beq	$a2, '/', div_logical
	
add_logical: 
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	li	$s7, 0	
	li	$s1, 0	# I
	li	$s0, 0	# S
	li 	$a2, 0
	extract_nth_bit($a2, $zero, $s7) # s7 is the first bit of a2
	
	j add_sub_logical
	
sub_logical:	
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	not	$a1, $a1
	li	$s0,  0	#Ith
	li	$s1,  0	#S
	li	$a2, 0xFFFFFFFF
	li	$s7, 0	# s7 = c
	extract_nth_bit($a2, $zero, $s7)	# s7 is the first bit of $a2[0]



add_sub_logical:	
	
	extract_nth_bit($a0, $s1, $s3)	
	extract_nth_bit($a1, $s1, $s4) 	
	xor	$s6, $s4, $s3		
	and	$t1, $s7, $s6		
	and	$t0, $s4, $s3		
	xor	$s5, $s7, $s6		
	or	$s7, $t0, $t1		
	insert_one_to_nth_bit($s5, $s0, $s1, $t9)	
	addi	$s1, $s1, 1	
			
	blt 	$s1, 32, add_sub_logical	
	move 	$v0, $s0		
	
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60

	jr	$ra
	


mult_logical:
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp) 
	sw $s2, 32($sp) #H
	sw $s1, 28($sp) #multiplier (L)
	sw $s0, 24($sp) #multiplicand (M)
	
	sw $a3, 20($sp) 
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	
	move $s1, $a0          
	move $s0, $a1            
	move $s4, $zero	 	#I 
	move $v0, $zero	
	move $v1, $zero 	#H

	ori $t2, $zero, 31		#preset t2 to 31
	extract_nth_bit($s0, $t2, $s2)	# s2 is multiplier[31]
	extract_nth_bit($s1, $t2, $t1)	# t1 is v1[31}
	beq $t1, 0, check_for_twos_comp
	
	move $t5, $a0
	move $t6, $a1
	move $a0, $s1
	jal twos_complement_64bit
	move $s1, $v0
	move $a0, $t5
	move $a1, $t6
	
	
check_for_twos_comp:
	beq $s2, 0, JUMP
	
	move $t5, $a0
	move $t6, $a1
	move $a0, $s0
	jal twos_complement_64bit
	move $s0, $v0
	move $a0, $t5
	move $a1, $t6
	
			
							
JUMP:
	extract_nth_bit($s0, $zero, $s2)		
	beq $s2, 1, negative
	
positive:
		li $s2, 0			
		j stop
negative:	
		li $s2, 0xffffffff
stop:
	and $s2, $s1, $s2			# X =  M & R
	
						
	move	$t8, $v0
	move	$t7, $a1
	move	$t6, $a0
	move	$a0, $v1
	move	$a1, $s2
	jal 	add_logical			# H = H + X 
	move	$v1, $v0
	move	$v0, $t8
	move	$a0, $t6
	move	$a1, $t7
	srl $s0, $s0, 1				# L >> 1
	srl $v0, $v0, 1				
	

	ori $t2, $zero, 31
	extract_nth_bit($v1,$zero, $t1)		# Extracts L[31] from preset value of t2
	insert_one_to_nth_bit( $t1, $v0, $t2, $t3)		# L[31] = H[0]			
	srl $v1, $v1, 1				# H >> 1
	
	addi $s4, $s4, 1			# I+1 
	bne $s4, 32, JUMP			# Checks if I==32, no then jump back to start of loop

	extract_nth_bit( $a1, $t2, $s2)		
	extract_nth_bit( $a0, $t2, $t1)		
	xor $s2, $t1, $s2
	beq $s2, 0, EXIT			#if there is no need to two's complement, jumps to exit
	move $t5, $a0
	move $t6, $a1
	move $a0, $v0
	jal twos_complement_64bit		#if there is, then reverts to twos complement and then exits
	
	move $a0, $t5
	move $a1, $t6
	
	not $v1, $v1
EXIT:	

	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60

	jr	$ra

twos_complement_64bit:
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	not	$a0, $a0	# inverse
	li	$a1, 1 		# preset a1 to 1
	la	$a2, '+'
	jal 	au_logical	# add
	
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60
	# Return to Caller
	jr	$ra


two_complement_if_neg:
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	bge	$a0, 0, ignore_twos	# if a0 is +, then twos complements are ignored
	jal 	twos_complement_64bit	# if not, then go to twos_complement_64bit
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60
	# Return to Caller
	jr	$ra
	
	
ignore_twos:
	move	$v0, $a0	# return a0
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60
	# Return to Caller
	jr	$ra

# division

div_logical:
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	move	$s7, $a0	# s5 and s7 are containers 
	move	$s5, $a1	# holds a0 and a1
	jal two_complement_if_neg
	move	$s0, $v0	
	move	$a0, $a1	
	jal two_complement_if_neg
	move	$s1, $v0	
	move	$a0, $s0
	move	$a1, $s1
	jal	div_unsigned
	move	$s2, $v1	
	move	$s3, $v0	
	ori	$t4, $zero, 31
	extract_nth_bit($s7, $t4, $t0)	
	extract_nth_bit($s5, $t4, $t1)	
	move	$a0, $s3	
	xor	$t6, $t0, $t1 		
	beq	$t6, 0, jump_the_number
	jal	twos_complement_64bit	
	move	$s0, $v0	
	j	complement_for_div
	
jump_the_number:	
	move	$s0, $a0		

complement_for_div:	
	ori	$t4, $zero, 31
	extract_nth_bit($s7, $t4, $t0)	
	move	$a0, $s2	
	beq	$t0, 0, move_on
	jal	twos_complement_64bit
	move	$a0, $v0
move_on:	
	move	$v1, $a0
	move	$v0, $s0	
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60
	
	jr	$ra
	

div_unsigned:
	addi $sp, $sp, -60 
	sw $fp, 60($sp)
	sw $ra, 56($sp)
	sw $s7, 52($sp)
	sw $s6, 48($sp)
	sw $s5, 44($sp)
	sw $s4, 40($sp)
	sw $s3, 36($sp)
	sw $s2, 32($sp)
	sw $s1, 28($sp)
	sw $s0, 24($sp)
	sw $a3, 20($sp)
	sw $a2, 16($sp)
	sw $a1, 12($sp)
	sw $a0, 8($sp)
	addi $fp, $sp, 60
	
	move	$s1, $a1	
	move	$s0, $a0	
	li	$s3, 0	
	li	$s2, 0	
	
JUMP_D:

	sll	$s2, $s2, 1	#s2 = R
	ori	$t4, $zero, 31	
	extract_nth_bit($s0, $t4, $t0)	#S0 = quotent
	insert_one_to_nth_bit($t0, $s2, $zero, $t9)	
	sll	$s0, $s0, 1	
	move	$a0, $s2	
	move	$a1, $s1	#s1 = D
	jal	sub_logical	
	move	$s4, $v0	
	blt	$s4, 0, Exit_Div	
	move	$s2, $s4 #s2 = R, $s4 = S	
	
	addi	$t4, $zero, 1
	
	
	insert_one_to_nth_bit( $t4, $s0, $zero, $t9)	

Exit_Div:	
	addi	$s3, $s3, 1  #s3 = I
	blt	$s3, 32, JUMP_D
	move	$v0, $s0	
	move	$v1, $s2	
	lw $fp, 60($sp)
	lw $ra, 56($sp)
	lw $s7, 52($sp)
	lw $s6, 48($sp)
	lw $s5, 44($sp)
	lw $s4, 40($sp)
	lw $s3, 36($sp)
	lw $s2, 32($sp)
	lw $s1, 28($sp)
	lw $s0, 24($sp)
	lw $a3, 20($sp)
	lw $a2, 16($sp)
	lw $a1, 12($sp)
	lw $a0, 8($sp)
	addi $sp, $sp, 60
	
	jr	$ra
	
