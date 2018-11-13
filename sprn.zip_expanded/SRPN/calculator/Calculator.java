package calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

public class Calculator {
	Stack<Integer> operands = new Stack<Integer>();
	     
    /**
     * 
     * @param equationStr
     * @return the result of the equation. If there was a error then it will return null
     */
    public int processEquation(LinkedList<String> equationQueue) throws EmptyStackException {
    	
    	boolean notFoundEquals = true;
    	String currentVal = ""; //reduces stack accessing
    	int newVal = 0;
    	
    	while(notFoundEquals) {
    		currentVal = equationQueue.peek();
    		if (Utilities.isAnInt(currentVal)) {
    			//takes off the first item and adds it to the stack
    			operands.add(Utilities.parseIntDefault(equationQueue.pop(),0));
    			
    		} else { //is an operand, d, or =
    			if (currentVal.equals("=")) { // found an =	
    				notFoundEquals = false;
    			} else {
    				if (operands.size() >= 2) {
    					/* removes the currentVal (an operator) from the queue
    					 * and two operands from the stack (if there are enough items otherwise throw an EmptySteakException)
    					 */
    					newVal = doOperation(operands.pop(), operands.pop(), equationQueue.pop());
    					operands.push(newVal);
    				} else {
    					throw new EmptyStackException();
    				}
    			}
    		}
    	}
    	return operands.peek();
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
	    		if (numb1 >= 0) {
	    			output = (int) Math.pow(numb2, numb1);
	    		} else {
	    			System.out.println("Negative power.");
	    			output = -1;
	    		}
	    		break;
	    	case "%" :
	    		output = numb2 % numb1;
	    		break;
    	}
		return output;
    }
    
}