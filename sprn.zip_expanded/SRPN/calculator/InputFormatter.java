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
    			isNegative = true;
    			
    		} else if (currentChar == '+' || currentChar == '-' ||
    				currentChar == '*' || currentChar == '/' || 
    				currentChar == '^' || currentChar == '%' ) { // if it is an operator
    			
    			equationList.add(Character.toString(currentChar));
			
    		} else if (currentChar == '=' || currentChar == 'd') {
    			equationList.add(Character.toString(currentChar));
    			System.out.println(processor.processEquation(equationList));
    			equationList.clear();
    		} else if (currentChar == '#' && equationStr.charAt(i+1) == ' ') {
    			isCommenting = true;
    			
    		} else if (!Utilities.isAnInt(currentChar) && currentChar != ' ') {
    			//if it is an invalid character
    			System.out.println("Unrecognised operator or operand \"" 
    					+ Character.toString(currentChar) + "\".");
    			
    		}
    	}
    	return equationList;
    }
}
