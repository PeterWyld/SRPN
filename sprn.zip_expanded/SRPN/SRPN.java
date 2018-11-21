import java.io.*;

public class SRPN {    
    public static void main(String[] args) {
    	RPNCalculator srpn = new RPNCalculator();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            //Keep on accepting input from the command-line
            while(true) {
                String command = reader.readLine();
                
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null) {
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }
                
                //Otherwise process the line
                srpn.processLine(command);          
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}

/* REFERENCES
 * Joren, 2009. How should I throw a divide by zero exception in Java without actually dividing by zero?
 * [Online]. StackOverflow. Available from: https://stackoverflow.com/questions/1657887/how-should-i-throw-a-divide-by-zero-exception-in-java-without-actually-dividing?answertab=votes#tab-top
 * 		The source influenced me to use a IllegalArguementException as well as telling me
 * 		how I could put a message in the exception.
 */	
