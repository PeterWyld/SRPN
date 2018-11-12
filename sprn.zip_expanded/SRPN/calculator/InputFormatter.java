package calculator;

import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author pw566
 * A class designed to format a string into LinkedList for the RPN calculator
 */
public class InputFormatter {
	private LinkedList<String> equationList = new LinkedList<String>();
	private Random randInt = new Random();
	
	//keeps a record of current character to reduce searching of linked list
	private char currentChar = ' ';
	
	//check for if a new integer entry has started (otherwise ++ would turn to +, ,+
	private boolean makingEntry = false;
	
	private int newIntEntry = 0;
	
	private String equationStr = "";
	
	private boolean isNegative = false;
	
	public InputFormatter(String equationStr) {
		this.equationStr = equationStr;
	}
	
	private void endAndAddIntEntry() {
		
	}
	
    /**
     * A function that will take a full equation in the form of a string and
     * turn it into a list of numbers and operations in the form of strings
     * @param equationStr: the equation in string form
     * @return equationList: a list of numbers and operations in separate indexes
     */
    public LinkedList<String> strEquationToList() {
    	

    	for (int i = 0; i <= equationStr.length() -1; i++) {
    		currentChar = equationStr.charAt(i);
    		
    		if (Utilities.isAnInt(currentChar)) {
    			makingEntry = true;
    			newIntEntry *= 10;
    			newIntEntry += Utilities.parseIntDefault(Character.toString(currentChar), 0);
    		} else {
    			if (makingEntry) {
    				if (isNegative) {
    					newIntEntry *= -1;
    				}
    				equationList.add(Integer.toString(newIntEntry));
    				makingEntry = false;
    				newIntEntry = 0;
    			}
    		}
    
    		if (currentChar == 'r') {
    			equationList.add(Integer.toString(randInt.nextInt()));
    			
    		} else if (currentChar == 'd') {
    			//whatever d does
    			
    		} else if (currentChar == '-' && Utilities.isAnInt(equationStr.charAt(i+1))) { //checking whether it is minus operator or negative operand
    			isNegative
    			
    		} else if (currentChar == '+' || currentChar == '-' ||
    				currentChar == '*' || currentChar == '/' || 
    				currentChar == '^' || currentChar == '%' ||
    				currentChar == '=') { // if it is an operator or =
    			if (makingEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				makingEntry = false;
    			}
    			equationList.add(Character.toString(currentChar));
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == ' ') {
    			if (makingEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				makingEntry = false;
    			}
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (!Utilities.isAnInt(currentChar)) {
    			//if it is an invalid character
    			System.out.println("Unrecognised operator or operand \"" 
    					+ Character.toString(currentChar) + "\".");
    			
    		} else { //must be an integer digit to reach this point
    			
    		}
    	}
    	return equationList;
    }
}
