/**
 * 
 * 
 *
 */
public class RPNCalculator {
	private FixedSizeStack<Double> operands = new FixedSizeStack<Double>(22);
	private Processor processor = new Processor();
	private RPNFormatter formatter = new RPNFormatter();
	
    /**
     * Takes in a string equation and attempts to process the line
     * @param equationStr
     * @return the result of the equation. If there was a error then it will return null
     */
    public void processLine(String equation) {    	
    	Object currentVal;
    	double newVal = 0;
    	
    	//operands.toArray returns an object array so has to be a Object[] as opposed to a Double[]
    	Object[] stackArr;
    	
    	/*
    	 * used for temporarily storing doubles popped off the stack so if there is
    	 * an exception in doOperation the values can then be pushed back onto the
    	 * stack
    	 */
    	Double temp1 = 0.0;
    	Double temp2 = 0.0;
    	
    	formatter.setString(equation);
    	currentVal = formatter.nextVal();
    	
    	while(currentVal != null) {	
    		if (currentVal instanceof Double) {
    			try {
    				operands.push((Double) currentVal);
    			} catch (StackOverflowError e) {
    				System.out.println("Stack overflow.");
    			}
    			
    			
    		} else if (currentVal.equals('=')) {
    			if (!operands.isEmpty()) {
    				System.out.println((int) (double) operands.peek());
    			} else {
    				System.out.println("Stack empty.");
    			}
    		
    		//if the value = d then display the stack
    		} else if (currentVal.equals('d')) {
    			stackArr = operands.toArray();
    			
    			if (stackArr.length == 0) {
    				System.out.println(Integer.MIN_VALUE);
    			}
    			
				for (int j = 0; j <= stackArr.length -1 ; j++) {
					System.out.println((int) (double) stackArr[j]);
				}
				
    		} else if (processor.isOperator((Character) currentVal)) {
    			if (operands.size() >= 2) {
					/* removes the currentVal (an operator) from the queue
					 * and two operands from the stack (if there are enough items
					 * otherwise throw an EmptySteakException)
					 */
					temp2 = operands.pop();
					temp1 = operands.pop();
					
					try {
    					newVal = processor.doOperation(temp1, temp2, (Character) currentVal);
    					operands.push(newVal);
					} catch (IllegalArgumentException e) {
						operands.push(temp1);
						operands.push(temp2);
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