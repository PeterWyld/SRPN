

public class RPNCalculator {
	private FixedSizeStack<Integer> operands = new FixedSizeStack<Integer>(23);
	private Processor processor = new Processor();
	RPNFormatter formatter = new RPNFormatter();
	
    /**
     * 
     * @param equationStr
     * @return the result of the equation. If there was a error then it will return null
     */
    public void processLine(String equation) {
    	
    	Object currentVal = ""; //reduces queue accessing
    	formatter.setString(equation);
    	int newVal = 0;
    	Object[] stackArr;
    	int temp1 = 0;
    	int temp2 = 0;
    	
    	currentVal = formatter.nextVal();
    	while(currentVal != null) {
    		
    		if (currentVal instanceof Integer) {
    			//takes off the first item and adds it to the stack
    			try {
    				operands.push((Integer) currentVal);
    			} catch (StackOverflowError e) {
    				System.out.println("Stack overflow.");
    			}
    			
    			
    		} else if (currentVal.equals('=')) {
    			System.out.println(operands.peek());
    			
    		} else if (currentVal.equals('d')) {
    			stackArr = operands.toArray(); //print out the stack
    			if (stackArr.length == 0) {
    				System.out.println(Integer.MIN_VALUE);
    			}
				for (int j = 0; j <= stackArr.length -1 ; j++) {
					System.out.println(stackArr[j]);
				}
				
    		} else if (processor.isOperator((Character) currentVal)) { // if its an operator
    			if (operands.size() >= 2) {
					/* removes the currentVal (an operator) from the queue
					 * and two operands from the stack (if there are enough items
					 * otherwise throw an EmptySteakException)
					 */
					temp1 = operands.pop();
					temp2 = operands.pop();
					try {
    					newVal = processor.doOperation(temp1, temp2, (Character) currentVal);
    					operands.push(newVal);
					} catch (IllegalArgumentException e) {
						operands.push(temp2);
						operands.push(temp1);
						System.out.println(e.getMessage());
					}
				} else { 
					System.out.println("Stack underflow.");
				}
    		}
    		currentVal = formatter.nextVal();
    	}
    }
    
    
}
