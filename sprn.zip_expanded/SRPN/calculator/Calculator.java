package calculator;

import java.util.Arrays;
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
    public void processEquation(LinkedList<String> equationQueue) throws EmptyStackException {
    	
    	String currentVal = ""; //reduces stack accessing
    	int newVal = 0;
    	Object[] stackArr;
    	
    	for(int i = 0; i <= equationQueue.size() -1; i++) {
    		currentVal = equationQueue.peek();
    		if (Utilities.isAnInt(currentVal)) {
    			//takes off the first item and adds it to the stack
    			operands.add(Utilities.parseIntDefault(equationQueue.pop(),0));
    			
    		} else { //is an operand, d, or =
    			if (currentVal.equals("=")) { // found an =	
    				System.out.println(operands.peek());
    			} else {
    				if (isOperator(currentVal.charAt(0))) {
						if (operands.size() >= 2) {
							/* removes the currentVal (an operator) from the queue
							 * and two operands from the stack (if there are enough items otherwise throw an EmptySteakException)
							 */
							try {
		    					newVal = doOperation(operands.pop(), operands.pop(), equationQueue.pop());
		    					operands.push(newVal);
							} catch (IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						} else { 
							throw new EmptyStackException();
						}
    				} else { //if the val is d
    					stackArr = operands.toArray();
    					for (int j = 0; j <= stackArr.length -1 ; j++) {
    						System.out.println(stackArr[i]);
    					}
    				}
    			}
    		}
    	}
    }
    
    //https://stackoverflow.com/questions/1657887/how-should-i-throw-a-divide-by-zero-exception-in-java-without-actually-dividing
    //COMPLETE CITATION
    
    
    
    public int doOperation(int numb1, int numb2, String operation) {
    	//if there is an error pushes numbers back onto stack
    	
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
	    		if (numb1 == 0) {
	    			operands.push(numb2);
	    			operands.push(numb1);
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else {
	    			output = numb2 / numb1;
	    		}
	    		break;
	    	case "^" :
	    		if (numb1 >= 0) {
	    			output = (int) Math.pow(numb2, numb1);
	    		} else {
	    			operands.push(numb2);
	    			operands.push(numb1);
	    			throw new IllegalArgumentException("Negative power.");
	    		}
	    		break;
	    	case "%" :
	    		if (numb2 == 0) {
	    			operands.push(numb2);
	    			operands.push(numb1);
	    			throw new IllegalArgumentException("Divide by 0.");
	    		} else if (numb1 == 0) {
	    			System.out.println("Floating point exception");
	    			System.exit(0);
	    		}
	    		output = numb2 % numb1;
	    		break;
    	}
		return output;
    }
    
    public int octalToDenary(int octal) {
    	int standardFormExponent = (int) (Math.pow(10, Math.floor(Math.log10(octal))));
    	int output = 0;
		for (int digit = standardFormExponent; digit >= 1; digit /= 10) {
			output += octal/digit - (octal/(digit*10))*10;
			output *= 8;
		}
		output /= 8;
		return output;
    }
    
    public boolean isOperator(char operator) {
    	if (operator == '+' || operator == '-' ||
    				operator == '*' || operator == '/' || 
    				operator == '^' || operator == '%') {
    		return true;
    	} else {
    		return false;
    	}
    
    }
}