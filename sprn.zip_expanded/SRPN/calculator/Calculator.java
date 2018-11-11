package calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Calculator {
	private StringBuilder equationFormer = new StringBuilder();
	private boolean commenting = false;
	
	
    public void processLine(String s) {
    	int result = 0;
        //If an "=" is received, print zero always
    	if (s.length() > 0) {
    		equationFormer.append(s);
        	equationFormer.append(" "); //used as a separator in the same way as a newline
	        if (s.charAt(s.length()-1) == '=') {
	            System.out.println(equationFormer.toString());
	            result = processEquation(equationFormer.toString());
	            System.out.println(result);
	            equationFormer = new StringBuilder();
	         
	        	equationFormer.append(s);
	        	equationFormer.append(" "); //used as a seperator in the same way as a newline
	        }
    	}
    }
    
    /**
     * 
     * @param equationStr
     * @return the result of the equation. If there was a error then it will return null
     */
    public int processEquation(String equationStr) {
    	LinkedList<String> equationQueue = strEquationToList(equationStr); //used as a queue
    	Stack<Integer> operands = new Stack<Integer>();
    	
    	equationQueue.add("="); //Convenient sign of when the operation has ended
    	boolean notFoundEquals = true;
    	String currentVal = ""; //reduces stack accessing
    	int newVal = 0;
    	
    	while(notFoundEquals) {
    		currentVal = equationQueue.peek();
    		if (isStringAnInt(currentVal)) {
    			//takes off the first item and adds it to the stack
    			operands.add(parseIntDefault(equationQueue.pop(),0));
    			
    		} else { //is an operand, d, or =
    			if (currentVal.equals("=")) { // found an =	
    				notFoundEquals = false;
    			} else {
    				try {
    					/*removes the currentVal (an operator) from the queue
    					 * and two operands from the 
    					 */
    					newVal = doOperation(operands.pop(), operands.pop(), equationQueue.pop());
    					operands.push(newVal);
    				} catch (EmptyStackException e) {
    					System.out.print("Stack underflow");
    					return -1;
    				}
    			}
    		}
    	}
    	return operands.pop();
    }
    
    public int doOperation(int numb1, int numb2, String operation) {
    	int output = 0;
    	switch (operation) {
	    	case "+" :
	    		output = numb2 + numb1;
	    		break;
	    	case "-" :
	    		output = numb2 - numb1;
	    		break;
	    	case "*" :
	    		output = numb2 * numb1;
	    		break;
	    	case "/" :
	    		output = numb2 / numb1;
	    		break;
	    	case "^" :
	    		output = (int) Math.pow(numb2, numb1);
	    		break;
	    	case "%" :
	    		output = numb2 % numb1;
	    		break;
    	}
		return output;
    }
    
    /**
     * A function that will take a full equation in the form of a string and
     * turn it into a list of numbers and operations in the form of strings
     * @param equationStr: the equation in string form
     * @return equationList: a list of numbers and operations in separate indexes
     */
    public LinkedList<String> strEquationToList(String equationStr) {
    	LinkedList<String> equationList = new LinkedList<String>();
    	Random randInt = new Random();
    	
    	//keeps a record of current character to reduce searching of linked list
    	char currentChar = ' ';
    	
    	//check for if a new integer entry has started (otherwise ++ would turn to +, ,+
    	boolean newEntry = false;
    	
    	//points to the first integer digit in the entry
    	int startOfEntry = 0;
    	//points to the character after the last integer in the entry
    	int endOfEntry = 0;
    	
    	for (int i = 0; i <= equationStr.length() -1; i++) {
    		currentChar = equationStr.charAt(i);
    
    		if (currentChar == 'r') {
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			equationList.add(Integer.toString(randInt.nextInt()));
    			startOfEntry = i + 1; // +1 to skip the character it was just on
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == 'd') {
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			//whatever d does I really have no idea
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == '+' || currentChar == '-' ||
    				currentChar == '*' || currentChar == '/' || 
    				currentChar == '^' || currentChar == '%' ||
    				currentChar == '=') { // if it is an operator or =
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			equationList.add(Character.toString(currentChar));
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == ' ') {
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (!isStringAnInt(Character.toString(currentChar))) {
    			//if it is an invalid character
    			System.out.println("Unrecognised operator or operand \"" 
    					+ Character.toString(currentChar) + "\".");
    			
    		} else { //must be an integer digit to reach this point
    			newEntry = true;
    			endOfEntry++;
    		}
    	}
    	return equationList;
    }
    
    /**
     * checks if a given string is an int
     * @return true if it is false otherwise
     */
    public boolean isStringAnInt(String str) {
    	try {
    		Integer.parseInt(str);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
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