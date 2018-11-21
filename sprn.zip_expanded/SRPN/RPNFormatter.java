/**
 * This class will deal with the formatting of a line for rpn
 * The string for the class can be set with setString
 */
public class RPNFormatter {
	private int[] randInt = {1804289383, 846930886, 1681692777, 1714636915, 1957747793, 
			424238335, 719885386, 1649760492, 596516649, 1189641421, 1025202362,
			1350490027, 783368690, 1102520059, 2044897763, 1967513926, 1365180540,
			1540383426, 304089172, 1303455736, 35005211, 521595368};
	private int randIntIndex = 0;
	private Processor processor = new Processor();

	/**
	 * points to the current char in the array
	 * starts at -1 so that the index can be incremented the moment the while loop starts
	 * (this is done so each return doesn't need to be preceded by "index++;"
	 * it is an attribute so its value persists after nextVal calls
	 */
	private int index = -1;
	
	private char[] equationChars;
	
	//keeps a record if it is commenting so its state will persist after newlines
	private boolean isCommenting = false;
	
	/**
	 * Replaces the equationsChars with the value of equationStr <br>
	 * Then it resets the index so that the nextVal function will start at the start
	 * @param equationStr the new line to take values from
	 */
	public void setString(String equationStr) {
		equationStr = equationStr + " "; //used as a seperator 
		equationChars = equationStr.toCharArray(); //is "(equationStr + " ").toCharArray();" bad code?
		index = -1;
	}
	
	/**
     * A function that finds the next number, operation, d or = and returns it
     * @return A Double equal to the number read off the String or <br>
     * A Character equal to an operation, d or = <br>
     * And null if there is no next value <br>
     */
    public Object nextVal() {
    	//check for if a new double entry has started (otherwise ++ would turn to {"+","","+"}
    	boolean makingEntry = false;
    	
    	//holds the value of the next double to be entered
    	double newIntEntry = 0;
    	
    	//keeps note of whether the current double entry is negative
    	boolean isNegative = false;
    	
    	//if a number begins with 0 then it takes it in as an octal (e.g. 010 -> 8)
    	//before sending the value back the octal is converted to decimal
    	boolean isOctal = false;
    	
    	
    	while(index <= equationChars.length -2) {
    		index++;
    		
    		if (isCommenting) {
    			if (equationChars[index] == '#' && equationChars[index+1] == ' ') {
    				isCommenting = false;
    			}
    			
    		} else {
	    		if (processor.isAnInt(equationChars[index])) {
	    			
	    			//if the first char of an int is 0
	    			if (!makingEntry && equationChars[index] == '0') { 
	    				isOctal = true;
	    			}
	    			
	    			//cancels octal if number is not base 8
	    			if (isOctal && (equationChars[index] == '9' ||
	    					equationChars[index] == '8')) { 
	    				
	    				isOctal = false;
	    			}
	    			
	    			makingEntry = true;
	    			newIntEntry *= 10.0;
	    			newIntEntry += 1.0 * processor.parseIntDefault(
	    					Character.toString(equationChars[index]), 0);
	    	    	
	    		} else {
	    			if (makingEntry) {
	    				if (isOctal) {
	    					newIntEntry = processor.octalToDenary((int) newIntEntry);
	    				}
	    				
	    				if (isNegative) {
	    					newIntEntry *= -1;
	    				}
	    				
		    			//bounds entry between the int max and min val
		    	    	if (newIntEntry > Integer.MAX_VALUE) {
		    	    		newIntEntry = Integer.MAX_VALUE;
		    			} else if (newIntEntry < Integer.MIN_VALUE){
		    				newIntEntry = Integer.MIN_VALUE;
		    			}
	    				return Double.valueOf(newIntEntry);
	    			}
	    			
	    			//sends back one of 22 psuedo-random numbers
	    			if (equationChars[index] == 'r') {
	        			randIntIndex++;
	        			if (randIntIndex > 22) {
	        				randIntIndex = 0;
	        			}
	        			
	        			return Double.valueOf(randInt[randIntIndex]);
	        			
	        		} else if (equationChars[index] == '-') {
	        			//checking if it is a sign for a number otherwise it's an operator
	        			if (processor.isAnInt(equationChars[index+1])) { 
	        				isNegative = true;
	        			} else {
	        				return Character.valueOf(equationChars[index]);
	        			}
	        			
	        		} else if (equationChars[index] == '=' || equationChars[index] == 'd') {
	        			return (Character) equationChars[index];
	        			
	        		} else if (processor.isOperator(equationChars[index])) {
	        			//if an = is preceded by an operation the order is switched
	        			if (equationChars[index+1] == '='){
	        				equationChars[index+1] = equationChars[index];
	        				return (Character) '=';
	        			} else {
	        				return (Character) equationChars[index];
	        			}
	        			
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
    	
    	/* 
    	 * if it was in the middle of making an double when the loop ended,
    	 * that double still needs to be sent back
    	 */
    	if (makingEntry) {
    		return Double.valueOf(newIntEntry);
    	}
    	
    	//if there are no values left to return it returns null
    	return null;
    }
}
