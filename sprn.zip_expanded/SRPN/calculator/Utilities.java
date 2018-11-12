package calculator;

public class Utilities {
	
    /**
     * checks if a given string is an int
     * @return true if it is false otherwise
     */
    public static boolean isAnInt(String str) {
    	try {
    		Integer.parseInt(str);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * checks if a given char is an int
     * @return true if it is false otherwise
     */
    public static boolean isAnInt(char charac) {
    	return isAnInt(Character.toString(charac));
    }

    /**
     * Takes in a string and attempts to parse it and returns a default value if that fails
     * @param numb the string to be parsed
     * @param defaultVal the value that the return will default to if the parsing fails
     * @return Either the original String but as an int or the default value
     */
    public static int parseIntDefault(String numb, int defaultVal) {
    	try {
    		return Integer.parseInt(numb);
    	} catch (NumberFormatException e) {
    		return defaultVal;
    	}
    }
}
