
public class Processor {
	
	//https://stackoverflow.com/questions/1657887/how-should-i-throw-a-divide-by-zero-exception-in-java-without-actually-dividing
    //COMPLETE CITATION
	
	//do saturation citation
	 public int doOperation(int numb1, int numb2, Character operation) {
		float floatNumb1 = numb1;
		float floatNumb2 = numb2;
		//numbers are made into floats so that they can go above/below Integer.MAX/MIN_VALUE
		//when the result is cast to an int it will be capped appropriately
		
    	int output = 0;
    	switch (operation) {
	    	case '+' :
	    		output = (int) (floatNumb2 + floatNumb1);
	    		break;
	    	case '-' :
	    		output = (int) (floatNumb2 - floatNumb1);
	    		break;
	    	case '*' :
	    		output = (int) (floatNumb2 * floatNumb1);
	    		break;
	    	case '/' :
	    		if (numb1 == 0) {
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else {
	    			output = (int) (floatNumb2 / floatNumb1);
	    		}
	    		break;
	    	case '^' :
	    		if (numb1 >= 0) {
	    			output = (int) Math.pow(numb2, numb1);
	    		} else {
	    			throw new IllegalArgumentException("Negative power.");
	    		}
	    		break;
	    	case '%' :
	    		if (numb2 == 0) {
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else if (numb1 == 0) {
	    			System.out.println("Floating point exception");
	    			System.exit(0);
	    		}
	    		output = (int) (floatNumb2 % floatNumb1);
	    		break;
    	}
		return output;
    }
	    
 	/**
 	 * Takes in an octal and converts it to a decimal number
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
     * Checks if a given char is one of +,-,/,*,%,^ and returns true if it is
     * otherwise it returns false
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
     * checks if a given string is an int
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
