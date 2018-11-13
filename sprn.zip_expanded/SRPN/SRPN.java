import java.io.*;
import calculator.InputFormatter;

public class SRPN {    
    public static void main(String[] args) {
    	InputFormatter srpn = new InputFormatter();

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
                
                //Otherwise, (attempt to) process the character
                srpn.processLine(command);          
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}
