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
	
	//points to the first integer digit in the entry
	private int startOfEntry = 0;
	//points to the character after the last integer in the entry
	private int endOfEntry = 0;
	//Details how long an entry is
	private int lengthOfEntry = 0;
	
	public void endAndInputNewEntry(String entry, int currentIndex) {
		if (makingEntry) {
			equationList.add(entry);
			makingEntry = false;
		}
		equationList.add(Integer.toString(randInt.nextInt()));
		startOfEntry = currentIndex + 1; // +1 to skip the character it was just on
		endOfEntry = currentIndex + 1;
	}
	
    /**
     * A function that will take a full equation in the form of a string and
     * turn it into a list of numbers and operations in the form of strings
     * @param equationStr: the equation in string form
     * @return equationList: a list of numbers and operations in separate indexes
     */
    public LinkedList<String> strEquationToList(String equationStr) {

    	for (int i = 0; i <= equationStr.length() -1; i++) {
    		currentChar = equationStr.charAt(i);
    
    		if (currentChar == 'r') {
    			
    			
    		} else if (currentChar == 'd') {
    			if (makingEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				makingEntry = false;
    			}
    			//whatever d does I really have no idea
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == '-') { //checking whether it is minus operator or negative operand
    			if (Utilities.isAnInt(equationStr.charAt(i+1))) { //is negative int
    				if (makingEntry) {
        				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
        				makingEntry = false;
        			}
    				startOfEntry = i + 1;
        			endOfEntry = i + 1;
    			}
    			
    		} else if (currentChar == '+' ||
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
    			if (makingEntry) {
    				
    			}
    			makingEntry = true;
    			endOfEntry++;
    		}
    	}
    	return equationList;
    }
}
