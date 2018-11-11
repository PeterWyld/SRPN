import java.io.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */

public class SRPN {
	private StringBuilder equationFormer = new StringBuilder();
	
    public void processLine(String s) {
        //If an "=" is received, print zero always
    	if (s.length() > 0) {
	        if (s.charAt(s.length()-1) == '=') {
	            System.out.println(equationFormer.toString());
	           
	            System.out.println(processEquation(equationFormer.toString()));
	            equationFormer = new StringBuilder();
	            
	        } else {
	        	equationFormer.append(s);
	        	equationFormer.append(" ");
	        }
    	}
    }
    

    public int processEquation(String equationStr) {
    	LinkedList<String> equationList = strEquationToList(equationStr);
    	equationList.add("="); //Convenient sign of when the operation has ended
    	boolean notFoundEquals = true;
    	String currentVal = "";
    	int index = 0;
    	int newVal = 0;
    	
    	while(notFoundEquals) {
    		currentVal = equationList.get(index);
    		if (!isStringAnInt(currentVal)) { // found an operator or =
    			if (currentVal.equals("=")) { // found an =	
    				notFoundEquals = false;
    			} else {
    				newVal = doOperation(parseIntDefault(equationList.get(index-2), 0),
    						parseIntDefault(equationList.get(index-1), 0),
    						equationList.get(index));
    				
    				equationList.set(index, Integer.toString(newVal));
    				equationList.remove(index-2);
    				//remains as index -2 due to the list changing size
    				equationList.remove(index-2); 
    				
    				//setting the index back due to list shortening
    				index -= 2;
    				
    			}
    		}
    		index++;
    	}
    	return parseIntDefault(equationList.get(equationList.size()-2), 0);
    }
    
    public int doOperation(int numb1, int numb2, String operation) {
    	int output = 0;
    	switch (operation) {
    	case "+" :
    		output = numb1 + numb2;
    		break;
    	case "-" :
    		output = numb1 - numb2;
    		break;
    	case "*" :
    		output = numb1 * numb2;
    		break;
    	case "/" :
    		output = numb1 / numb2;
    		break;
    	case "^" :
    		output = (int) Math.pow(numb1, numb2);
    		break;
    	case "%" :
    		output = numb1 % numb2;
    		break;
    	}
		return output;
    }
    
    /**
     * A function that will take a full equation in the form of a string and
     * turn it into a list of numbers and operations
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
    				currentChar == '*' || currentChar == '/') { // if it is an operator
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			equationList.add(Character.toString(currentChar));
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
    		} else if (currentChar == ' ' || !isStringAnInt(Character.toString(currentChar))) {
    			if (newEntry) {
    				equationList.add(equationStr.substring(startOfEntry, endOfEntry));
    				newEntry = false;
    			}
    			startOfEntry = i + 1;
    			endOfEntry = i + 1;
    			
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
    
    public static void main(String[] args) {
        SRPN sprn = new SRPN();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            //Keep on accepting input from the command-line
            while(true) {
                String command = reader.readLine();
                
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null)
                {
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }
                
                //Otherwise, (attempt to) process the character
                sprn.processLine(command);          
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}
