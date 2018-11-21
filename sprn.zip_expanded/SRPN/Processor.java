/**
 * The processor class contains some basic Mathematical methods
 */
public class Processor {
	
	/**
	 * This function can be used to do an operation on two numbers
	 * <br> NOTE: if floatNumb2 == 0 and the operation is % then it will result in a system exit
	 * @param floatNumb1 The first number in the operation
	 * @param floatNumb2 The second number in the operation
	 * @param operation The operation to be performed
	 * @return the result of the operation bounded by the max and min values of an integer
	 * @throws IllegalArgumentException If the operation would attempt to divide by zero,
	 * put something to a negative power or if operation is unrecognised (not a member of {+,-,*,/,%,^})
	 * <br>
	 * Citations: <br>
	 *  (Joren, 2009): Use of IllegalArgumentException and exception messages <br>
	 */
	 public double doOperation(double floatNumb1, double floatNumb2, Character operation) {
    	double output = 0;
    	switch (operation) {
	    	case '+' :
	    		output = (floatNumb1 + floatNumb2);
	    		break;
	    	case '-' :
	    		output = (floatNumb1 - floatNumb2);
	    		break;
	    	case '*' :
	    		output = (floatNumb1 * floatNumb2);
	    		break;
	    	case '/' :
	    		if (floatNumb1 == 0.0) {
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else {
	    			output = (floatNumb1 / floatNumb2);
	    		}
	    		break;
	    	case '^' :
	    		if (floatNumb1 >= 0.0) {
	    			output = Math.pow(floatNumb1, floatNumb2);
	    		} else {
	    			throw new IllegalArgumentException("Negative power.");
	    		}
	    		break;
	    	case '%' :
	    		if (floatNumb2 == 0) {
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else if (floatNumb2 == 0) {
	    			System.out.println("Floating point exception");
	    			System.exit(0);
	    		}
	    		output = (floatNumb1 % floatNumb2);
	    		break;
    		default:
    			throw new IllegalArgumentException("Unrecognised operator");
    	}
    	
    	if (output > Integer.MAX_VALUE) {
			output = Integer.MAX_VALUE;
		} else if (output < Integer.MIN_VALUE){
			output = Integer.MIN_VALUE;
		}
		return output;
    }
	    
 	/**
 	 * Takes in an octal and converts it to a decimal number <br>
 	 * If the number has a 9 or an 8 in it (i.e. it was actually a decimal number) then
 	 * it returns the original number
 	 * @param octal: a number in base 8
 	 * @return the octal converted to decimal or the original decimal number
 	 */
    public int octalToDenary(int octal) {
    	int standardFormExponent = (int) (Math.pow(10, Math.floor(Math.log10(octal))));
    	int output = 0;
    	int currentDigit = 0;
    	
		for (int digit = standardFormExponent; digit >= 1; digit /= 10) {
			currentDigit = octal/digit - (octal/(digit*10))*10;
			if (currentDigit == 9 || currentDigit == 8) {
				return octal;
			} else {
				output += currentDigit;
				output *= 8;
			}	
		}
		
		output /= 8; //because the loop above will *8 one too many times
		return output;
    }
    
    /**
     * Checks if a given char is one of +,-,/,*,%,^ 
     * @param operator : the char to be checked
     * @return true if operator is an operator, false otherwise
     */
    public boolean isOperator(char operator) {
    	if (operator == '+' || operator == '-' ||
    				operator == '*' || operator == '/' || 
    				operator == '^' || operator == '%') {
    		
    		return true;
    	} else {
    		return false;
    	}
    
    }
    
    /**
     * Checks if a given string is an int
     * @param str the string to be checked
     * @return true if it is false otherwise
     */
    public boolean isAnInt(String str) {
    	try {
    		Integer.parseInt(str);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * checks if a given char is an int
     * @return true if it is false otherwise
     */
    public boolean isAnInt(char charac) {
    	return isAnInt(Character.toString(charac));
    }

    /**
     * Takes in a string and attempts to parse it and returns a default value if that fails
     * @param numb the string to be parsed
     * @param defaultVal the value that the return will default to if the parsing fails
     * @return Either the original String but as an int or the default value
     */
    public int parseIntDefault(String numb, int defaultVal) {
    	try {
    		return Integer.parseInt(numb);
    	} catch (NumberFormatException e) {
    		return defaultVal;
    	}
    }
}
