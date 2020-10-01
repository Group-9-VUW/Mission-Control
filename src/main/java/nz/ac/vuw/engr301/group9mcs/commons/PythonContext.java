package nz.ac.vuw.engr301.group9mcs.commons;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.NOAAException;
import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.OWMGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;

/**
 * This class is responsible for checking the version of Python the user has installed.
 * This will be passed into the NOAAGetter so that the correct python command can be ran.
 *
 * @author Sai
 */
public class PythonContext {
    /**
     * This is the python command to run python on the users machine.
     * If the 'python3' command runs (or 'python'), then the user should be able to successfully
     * run the NOAA python script (provided that they have the required libraries).
     */
    public static String pythonCommand = "python3";

    /**
     * Checks if the user has the required python version (>= 3).
     *
     * @return true if the user has python version greater than or equal to 3, else false.
     */

    public static boolean hasValidPython() {
        // This will first try run the 'python3' command, if that does not exist
        // on the users machine, it will then try to run the normal 'python' command.
        String currentCommand = "python3 --version";
        try {
            Process process = Runtime.getRuntime().exec(currentCommand);
            InputStream is = process.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String pythonThreeOutput = reader.readLine();

                if (pythonThreeOutput != null) {
                    // If python3 gives output, then the user has python 3 installed.
                    return true;
                }
                // Else try to run the normal 'python' command.
                currentCommand = "python --version";
                return checkNormalPython();
            }
        } catch (@SuppressWarnings("unused") IOException e) {
            DefaultLogger.logger.error("Error running " + currentCommand);

            // If we have already checked both 'python3' and 'python', then return false.
            if (currentCommand.equals("python --version")) {
                return false;
            }
            // Otherwise, try to run the normal 'python' command.
            try {
                return checkNormalPython();
            } catch (@SuppressWarnings("unused") IOException normalPythonExeception) {
                DefaultLogger.logger.error("Could not run both 'python3 --version' and 'python --version'");
                return false;
            }
        }
    }

    /**
     * Checks the users python version using the normal 'python' command as opposed to 'python3'.
     *
     * @return true if the users python version is >= 3, false otherwise
     * @throws IOException if the 'python --version' command cannot be ran on the users machine.
     */
    private static boolean checkNormalPython() throws IOException {
        // The normal "python" command can also be used to check the python version,
        // so we check with that command aswell and verify if the version is python three.
        Process process = Runtime.getRuntime().exec("python --version");
        InputStream is = process.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String pythonVersion = reader.readLine();

            if (pythonVersion == null || !pythonVersion.contains("Python")) {
                DefaultLogger.logger.error("User does not have python installed.");
                return false;
            }

            String[] split = pythonVersion.split(" ");
            // Sample output here is
            // Python x.y.z
            // Checking the value of 'x' is sufficient enough to check if the user
            // has a python version >= 3 installed.
            if (Character.getNumericValue(split[1].charAt(0)) < 3) {
                DefaultLogger.logger.error("User has python version " + split[1] + ". Minimum python version" +
                        "needed to run rocketpyalpha is Python 3.");
                return false;
            }
            pythonCommand = "python";
            return true;
        }
    }

    /**
     * Checks if the user has the required python modules (rocketpyalpha and all of its dependencies) installed.
     * @return true if they do have them installed, false otherwise.
     * @throws IOException if the python script to check if they have the modules cannot run.
     */
    public static boolean hasRequiredModules() throws IOException{
        if (!hasValidPython()){
            return false;
        }
        
        // Run and check the output of the script, if it says "Modules are missing." then return false as the user
        // does not have the required modules. 
        try {
            Process process = Runtime.getRuntime().exec(pythonCommand + " scripts/check_has_modules.py");
            InputStream is = process.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
                for (String output = reader.readLine(); output != null; output = reader.readLine()){
                    System.out.println(output);
                    if(output.equals("Modules are missing.")){
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            DefaultLogger.logger.error("Error running check_has_modules.py");
            throw e;
        }

        return true;
    }

    /**
     * Installs the required modules for the user to run noaa.py
     * Main modules are rocketpyalpha and netCDF4, rocketpyalpha will also install its dependencies
     * such as numpy.
     * @return true if the modules were sucessfully installed, false otherwise.
     */
    public static boolean installRequiredModules() {
        if (!hasValidPython()){
            return false;
        }
        
        // Run the pythons script to install the modules. 
        // If the script cannot be ran, then throw an IOException. 
        try {
            Process process = Runtime.getRuntime().exec(pythonCommand + " scripts/install_modules.py");
            InputStream is = process.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
                for (String output = reader.readLine(); output != null; output = reader.readLine()){
                    System.out.println(output); // Printing to System out for now, should be displaying on corresponding View/Panel. 
                }
            }
        } catch (@SuppressWarnings("unused") IOException e) {
            DefaultLogger.logger.error("Error running install_modules.py");
            return false;
        }

        return true;
    }

    /**
     * Runs the NOAA forecast python script.
     * NOTE: User *must* pass the hasValidPython() and intallRequiredModules() checks
     * (i.e the user must have a python >= 3 and must have rocketpyalpha and netCDF4 installed on their machine.
     * @param latitude the latitude of the launch site
     * @param longitude the longitude of the launch site
     * @param daysAhead how far ahead the user wants the forecast (i.e provide 2 for daysAhead if they want the
     *                  forecast for two days from now.
     * @return a string with the forecast information.
     * @throws InvalidParameterException if the supplied latitude and longitude are invalid or daysAhead is <= 0.
     * @throws IOException if the noaa script could not be ran.
     */
    @SuppressWarnings("null")
	public static String runNOAA(double latitude, double longitude, int daysAhead, int utcTime) throws InvalidParameterException, IOException{
    	// Check if the supplied latitude and longitude are incorrect, if so then throw an InvalidParameterException. 
        try{
            OWMGetter.checkValidLatAndLon(latitude, longitude);
        } catch (InvalidParameterException e){
            DefaultLogger.logger.error(e.getMessage());
            throw e;
        }

        // Check for a valid daysAhead aswell, throw an InvalidParameterException if it is <= 0. 
        if(daysAhead < 0){
            String errorMessage = "Invalid 'daysAhead' parameter for retrieving forecast: " + daysAhead;
            DefaultLogger.logger.error(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }
        
        // Printing out to system for now, this will be displaying on the corresponding View/Panel. 
        System.out.println("Retrieving weather...");

        StringBuilder output = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(pythonCommand + " scripts/noaa.py "
                    +  latitude + " " + longitude + " " + daysAhead + " " + utcTime);
            InputStream is = process.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
                for (String forecastReading = reader.readLine(); forecastReading != null; forecastReading = reader.readLine()){
                    output.append(forecastReading).append("\n");
                }
            }
        } catch (IOException e) {
            DefaultLogger.logger.error("Error while trying to run noaa.py");
            throw e;
        }

        // If the output is empty or if the output does not contains a '[' (showing that the output
        // does not contain an array) then there was an error retrieving the output.
        if (output.toString().isEmpty() || !output.toString().contains("[")) {
            DefaultLogger.logger.error("Error retrieving NOAA weather data.");
            throw new NOAAException("Could not retrieve weather from NOAA.");
        }

        return output.toString();
    }

}
