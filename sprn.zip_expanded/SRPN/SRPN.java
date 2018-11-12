import java.io.*;

import calculator.Calculator;

public class SRPN {    
    public static void main(String[] args) {
        Calculator srpn = new Calculator();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(10/-3);
        
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
                srpn.processLine(command);          
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}
