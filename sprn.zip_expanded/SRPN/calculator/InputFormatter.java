package calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Random;

/**
 * 
 * @author pw566
 * A class designed to format a string into LinkedList for the RPN calculator
 */
public class InputFormatter {
	private LinkedList<String> equationList = new LinkedList<String>();
	private int[] randInt = {1804289383, 846930886, 1681692777, 1714636915, 1957747793, 
			424238335, 719885386, 1649760492, 596516649, 1189641421, 1025202362,
			1350490027, 783368690, 1102520059, 2044897763, 1967513926, 1365180540,
			1540383426, 304089172, 1303455736, 35005211, 521595368};
	private int randIntIndex = 0;

	private boolean isCommenting = false;
	private Calculator processor = new Calculator();
	
	public void processLine(String s) {

        //If an "=" is received, print zero always
		if (s.length() > 0) {
    		strEquationToList(" " + s + " ");

    	}
    }
	
    /**
     * A function that will take a full equation in the form of a string and
     * turn it into a list of numbers and operations in the form of strings
     * @param equationStr: the equation in string form
     * @return equationList: a list of numbers and operations in separate indexes
     */
    public LinkedList<String> strEquationToList(String equationStr) {
    	//keeps a record of current character to reduce searching of linked list
    	char currentChar = ' ';
    	
    	//check for if a new integer entry has started (otherwise ++ would turn to {"+","","+"}
    	boolean makingEntry = false;
    	
    	//holds the value of the next int to be entered
    	int newIntEntry = 0;
    	
    	//keeps note of whether the current int entry is negative
    	boolean isNegative = false;
    	
    	//if a number begins with 0 then it takes it in as an octal (e.g. 010 -> 8
    	boolean isOctal = false;
    	
    	
    	for (int i = 0; i <= equationStr.length() -1; i++) {
    		currentChar = equationStr.charAt(i);
    		
    		if (isCommenting) {
    			if (currentChar == '#' && equationStr.charAt(i+1) == ' ') {
    				isCommenting = false;
    			}
    			
    		} else {
    		
	    		if (Utilities.isAnInt(currentChar)) {
	    			if (!makingEntry && currentChar == '0') { //if the first char of an int is 0
	    				isOctal = true;
	    			}
	    			if (isOctal && (currentChar == '9' || currentChar == '8')) {
	    				isOctal = false;
	    			}
	    			makingEntry = true;
	    			newIntEntry *= 10;
	    			newIntEntry += Utilities.parseIntDefault(Character.toString(currentChar), 0);
	    		} else {
	    			if (makingEntry) {
	    				if (isNegative) {
	    					newIntEntry *= -1;
	    				}
	    				if (isOctal) {
	    					newIntEntry = processor.octalToDenary(newIntEntry);
	    				}
	    				equationList.add(Integer.toString(newIntEntry));
	    				makingEntry = false;
	    				newIntEntry = 0;
	    			}
	    			
	    			if (currentChar == 'r') {
	        			equationList.add(Integer.toString(randInt[randIntIndex]));
	        			randIntIndex++;
	        			if (randIntIndex > 22) {
	        				randIntIndex = 0;
	        			}
	        			
	        		} else if (currentChar == '-' && Utilities.isAnInt(equationStr.charAt(i+1))) { //checking whether it is minus operator or negative operand
	        			isNegative = true;
	        			
	        		} else if (processor.isOperator(currentChar) || currentChar == 'd' ) { // if it is an operator or d
	        			equationList.add(Character.toString(currentChar));
	    			
	        		} else if (currentChar == '=') {
	        			equationList.add(Character.toString(currentChar));
	        			try {
	        				processor.processEquation(equationList);
	        			} catch (EmptyStackException e) {
	        				System.out.println("Stack underflow.");
	        			} catch (StackOverflowError e) {
	        				System.out.println("Stack overflow.");
	        			}
	        			equationList.clear();
	        		} else if (currentChar == '#' && equationStr.charAt(i+1) == ' ') {
	        			isCommenting = true;
	        			
	        		} else if (!Utilities.isAnInt(currentChar) && currentChar != ' ') {
	        			//if it is an invalid character
	        			System.out.println("Unrecognised operator or operand \"" 
	        					+ Character.toString(currentChar) + "\".");
	        			
	        		}
	    		}
    		}
    	}
    	processor.processEquation(equationList);
    	return equationList;
    }
}
