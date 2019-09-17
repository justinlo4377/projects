.include "./cs47_proj_macro.asm"
.data
.text
.globl au_normal
# TBD: Complete your project procedures
# Needed skeleton is given
#####################################################################
# Implement au_normal
# Argument:
# 	$a0: First number	
#	$a1: Second number
#	$a2: operation code ('+':add, '-':sub, '*':mul, '/':div)  4 if statements
# Return:
#	$v0: ($a0+$a1) | ($a0-$a1) | ($a0*$a1):LO | ($a0 / $a1)
# 	$v1: ($a0 * $a1):HI | ($a0 % $a1)
# Notes:
#####################################################################
au_normal:
# TBD: Complete it
	beq $a2, '+', plus
	beq $a2, '-', minus
	beq $a2, '*', multiply
	beq $a2, '/', divide
	
plus:
	add $v0, $a0, $a1
	j exit
minus:
	sub $v0, $a0, $a1
	j exit
	
multiply:
	mul $v0, $a0, $a1
	mfhi $v1
	j exit
	
divide:
	div $v0, $a0, $a1
	mfhi $v1
	mflo $v0
	j exit
	
exit:
	jr	$ra
