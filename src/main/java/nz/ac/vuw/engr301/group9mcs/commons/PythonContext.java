package nz.ac.vuw.engr301.group9mcs.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is responsible for checking the version of Python the user has installed. 
 * This will be passed into the NOAAGetter so that the correct python command can be ran. 
 * @author Sai
 */
public class PythonContext {
	/**
	 * This is the python command to run python on the users machine. 
	 * It will default to "python" but this could be platform dependant so it can be 
	 * "py" aswell. 
	 */
    private String pythonCommand = "python";

    /**
     * Checks if the user has the required python version (> 3).
     * @return true if the user has python version greater than 3, else false.
     * @throws IOException if the command cannot be ran. 
     */
    @SuppressWarnings("static-method")
	public boolean hasValidPython() throws IOException{
        try {
            Process process = Runtime.getRuntime().exec("python --version");
            InputStream is = process.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String pythonVersion = reader.readLine();

            if(pythonVersion == null || !pythonVersion.contains("Python")){
                DefaultLogger.logger.error("User does not have python installed.");
                return false;
            }

            String[] split = pythonVersion.split(" ");
            if(Character.getNumericValue(split[1].charAt(0)) < 3){
                DefaultLogger.logger.error("User has python version " + split[1] + ". Minimum python version" +
                        "needed to run pyrocket is Python 3.");
                return false;
            }

            return true;
        } catch (IOException e) {
            DefaultLogger.logger.error("Error running 'python --version'");
            throw e;
        }
    }

	/**
	 * @return the pythonCommand
	 */
	public String getPythonCommand() {
		return this.pythonCommand;
	}

}
