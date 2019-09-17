# Add you macro definition here - do not touch cs47_common_macro.asm"
#<------------------ MACRO DEFINITIONS ---------------------->#
	.macro extract_nth_bit($regD, $regS, $regT) #container, source bit, position
	addi	$regT, $zero, 1		# t is set to one	
	sllv	$regT, $regT, $regS	# left shift t by s times 
	and	$regT, $regD, $regT	# gets the digits
	srlv	$regT, $regT, $regS	# move the digit to lsb
	.end_macro
	
	.macro insert_one_to_nth_bit ($regD, $regS, $regT, $maskR)
	li	$maskR, 1			# mask
	sllv	$maskR, $maskR, $regT		# Shift mask t times to the left
	not	$maskR, $maskR		# Inverting the mask
	and	$regS, $maskR, $regS		# the number AND mask
	sllv	$regD, $regD, $regT		# shift the number by t times
	or	$regS, $regS, $regD		# shifted number is in d
	.end_macro
	
	