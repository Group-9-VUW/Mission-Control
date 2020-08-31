package nz.ac.vuw.engr301.group9mcs.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class PythonContext {
    private String pythonCommand = "python";

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
}
