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
	 * If the python3 command runs, then the user should be able to successfully
     * run the NOAA python script (provided that they have the required libraries).
	 */
    public static String pythonCommand = "python3";

    /**
     * Checks if the user has the required python version (> 3).
     * @return true if the user has python version greater than 3, else false.
     * @throws IOException if the command cannot be ran. 
     */

	public static boolean hasValidPython() throws IOException{
        String currentCommand = "python3 --version";
        try {
            Process process = Runtime.getRuntime().exec(currentCommand);
            InputStream is = process.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String pythonThreeOutput = reader.readLine();

            if (pythonThreeOutput != null){
                //If python3 gives output, then the user has python 3 installed.
                return true;
            } else {
                //The normal "python" command can also be used to check the python version,
                //so we check with that command aswell and verify if the version is python three.
                process = Runtime.getRuntime().exec("python --version");
                is = process.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                String pythonVersion = reader.readLine();

                if(pythonVersion == null || !pythonVersion.contains("Python")){
                    DefaultLogger.logger.error("User does not have python installed.");
                    return false;
                }

                String[] split = pythonVersion.split(" ");
                // Sample output here is
                // Python x.y.z
                // Checking the value of 'x' is sufficient enough to check if the user
                // has a python version >= 3 installed.
                if(Character.getNumericValue(split[1].charAt(0)) < 3){
                    DefaultLogger.logger.error("User has python version " + split[1] + ". Minimum python version" +
                            "needed to run rocketpyalpha is Python 3.");
                    return false;
                } else {
                    pythonCommand = "python";
                    return true;
                }
            }
        } catch (IOException pythonThreeException) {
            DefaultLogger.logger.error("Error running " + currentCommand);

            //throw new IOException("Error running " + currentCommand);
            currentCommand = "python --version";
            try{
                return checkNormalPython();
            } catch (IOException normalPythonException){
                DefaultLogger.logger.error("Error running " + currentCommand);
                throw new IOException("Could not run both 'python3' and 'python'");
            }
        }
    }

    private static boolean checkNormalPython() throws IOException{

    }


    public static void main(String[] args) throws IOException{
        System.out.println(checkPythonThree());
    }
}
