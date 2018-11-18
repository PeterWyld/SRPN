public class RPNFormatter {
	private int[] randInt = {1804289383, 846930886, 1681692777, 1714636915, 1957747793, 
			424238335, 719885386, 1649760492, 596516649, 1189641421, 1025202362,
			1350490027, 783368690, 1102520059, 2044897763, 1967513926, 1365180540,
			1540383426, 304089172, 1303455736, 35005211, 521595368};
	private int randIntIndex = 0;
	private Processor processor = new Processor();

	private int index = -1;
	/**
	 * points to the current char in the array
	 * starts at -1 so that the index can be incremented the moment the while loop starts
	 * (this is done so each return doesn't need to be preceded by "index++;"
	 * it is an attribute so its value persists after nextVal calls
	 */
	
	private char[] equationChars;

	private boolean isCommenting = false;
	
	public void setString(String equationStr) {
		equationStr = equationStr + " ";
		equationChars = equationStr.toCharArray(); //is "(equationStr + " ").toCharArray();" bad code?
		index = -1;
	}
	
	/**
     * 
     * @return :an object that will be an Integer if it is a number
     * a Character if it is an operation, d or =
     * and null if there is no next val
     */
    public Object nextVal() {
    	//check for if a new integer entry has started (otherwise ++ would turn to {"+","","+"}
    	boolean makingEntry = false;
    	
    	//holds the value of the next int to be entered
    	int newIntEntry = 0;
    	
    	//keeps note of whether the current int entry is negative
    	boolean isNegative = false;
    	
    	//if a number begins with 0 then it takes it in as an octal (e.g. 010 -> 8
    	boolean isOctal = false;
    	
    	
    	while(index <= equationChars.length -2) {
    		index++;
    		if (isCommenting) {
    			if (equationChars[index] == '#' && equationChars[index+1] == ' ') {
    				isCommenting = false;
    			}
    			
    		} else {
    		
	    		if (processor.isAnInt(equationChars[index])) {
	    			if (!makingEntry && equationChars[index] == '0') { //if the first char of an int is 0
	    				isOctal = true;
	    			}
	    			if (isOctal && (equationChars[index] == '9' ||
	    					equationChars[index] == '8')) {
	    				isOctal = false;
	    			}
	    			makingEntry = true;
	    			newIntEntry *= 10.0;
	    			newIntEntry += 1.0 * processor.parseIntDefault(Character.toString(equationChars[index]), 0);
	    		} else {
	    			if (makingEntry) {
	    				if (isNegative) {
	    					newIntEntry *= -1;
	    				}
	    				if (isOctal) {
	    					newIntEntry = processor.octalToDenary(newIntEntry);
	    				}
	    				return Integer.valueOf(newIntEntry);
	    			}
	    			
	    			if (equationChars[index] == 'r') {
	        			randIntIndex++;
	        			if (randIntIndex > 22) {
	        				randIntIndex = 0;
	        			}
	        			return Integer.valueOf(randInt[randIntIndex]);
	        			
	        		} else if (equationChars[index] == '-') {
	        			if (processor.isAnInt(equationChars[index+1])) { 
	        				//checking whether it is a negative operand
	        				isNegative = true;
	        			} else if (equationChars[index+1] == '-') {
	        				//if there is a double minus change it to a plus
	        				equationChars[index + 1] = '+';
	        			} else {
	        				return Character.valueOf(equationChars[index]);
	        			}	        			
	        			
	        		} else if (processor.isOperator(equationChars[index]) || equationChars[index] == 'd'
	        				|| equationChars[index] == '=') { // if it is an operator, d or =
	        			return Character.valueOf(equationChars[index]);
	        			
	        		} else if (equationChars[index] == '#' && equationChars[index+1] == ' ') {
	        			isCommenting = true;
	        			
	        		} else if (equationChars[index] != ' ') {
	        			//if it is an invalid character
	        			System.out.println("Unrecognised operator or operand \"" 
	        					+ Character.toString(equationChars[index]) + "\".");
	        			
	        		}
	    		}
    		}
    	}
    	if (makingEntry) {
    		return Integer.valueOf(newIntEntry);
    	}
    	//if there are no values left to return it returns null
    	return null;
    }
}
