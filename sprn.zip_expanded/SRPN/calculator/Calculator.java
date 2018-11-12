package calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
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
	            //System.out.println(equationFormer.toString());
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
    	InputFormatter formatter = new InputFormatter();
    	LinkedList<String> equationQueue = formatter.strEquationToList(equationStr); //used as a queue
    	Stack<Integer> operands = new Stack<Integer>();
    	
    	equationQueue.add("="); //Convenient sign of when the operation has ended
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
    
}