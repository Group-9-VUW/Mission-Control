package nz.ac.vuw.engr301.group9mcs.externaldata.weather;

import nz.ac.vuw.engr301.group9mcs.commons.PythonContext;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.NOAAException;
import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Will run the NOAA python script, parse the output and return
 * NOAAWeatherData objects.
 *
 * @author Sai
 */
public class NOAA {

    /**
     * Stores the latest forecast reading.
     */
    public static List<NOAAWeatherData> currentForecast = new ArrayList<>();

    /**
     * Calls the NOAA python script and parses the returned weather data into a sorted List.
     *
     * @param latitude  the latitude of the launch site
     * @param longitude the longitude of the launch site
     * @param daysAhead how far ahead (in days from the current time) the user would like their forecast
     * @param date      a Date Time object of the date the user wants the forecast for.
     * @return a Map with the weather data.
     * @throws IOException if the user does not have Python or the required modules (see PythonContext.java)
     */
    public static List<NOAAWeatherData> getWeather(double latitude, double longitude, int daysAhead, Calendar date) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String output; 
        try {
           output = PythonContext.runNOAA(latitude, longitude, daysAhead, Integer.parseInt(sdf.format(date.getTime())));
        } catch (InvalidParameterException e) {
        	throw e;
        } catch (IOException e) {
        	throw e;
        } catch (NOAAException e) {
        	throw e; 
        }
        
        JSONArray array = new JSONArray(output);

        NOAA.currentForecast = getSortedList(array);
        return NOAA.currentForecast;
    }


    /**
     * Converts the supplied JSON weather readings into a Sorted List.
     *
     * @param jsonWeatherReadings the weather readings in JSON format
     * @return a list with the same data as jsonWeatherReadings, where the entires are sorted by altitudes.
     */
    public static List<NOAAWeatherData> getSortedList(JSONArray jsonWeatherReadings) {
        List<NOAAWeatherData> weatherReadings = new ArrayList<>();

        for (int i = 0; i < jsonWeatherReadings.length(); i++) {
            JSONObject currentReading = jsonWeatherReadings.getJSONObject(i);
            weatherReadings.add(new NOAAWeatherData(currentReading.getDouble("altitude"),
                    currentReading.getDouble("windSpeed"),
                    currentReading.getDouble("windDirection"),
                    currentReading.getDouble("temperature"),
                    currentReading.getDouble("pressure")));
        }

        Collections.sort(weatherReadings);

        return weatherReadings;
    }

    /**
     * Writes the latest forecast reading to a file.
     *
     * @param file the file to write the forecast to.
     * @throws IOException if the file cannot be written to (i.e. doesn't exist).
     */
    public static void writeToFile(File file) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);

            for (NOAAWeatherData data : NOAA.currentForecast) {
                writer.write(data.getAltitude() + "," + data.getWindSpeed() + "," + data.getWindDirection() +
                        "," + data.getTemperature() + "," + data.getPressure() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            DefaultLogger.logger.error("Error writing current forecast to file: " + file.getName());
            throw e;
        }
    }


    /**
     * Reads a forecast from a file.
     *
     * @param file the file the forecast should be read from.
     * @return a List of NOAAWeatherData objects created from the file.
     * @throws IOException if there is an error trying to read the file.
     */
    public static List<NOAAWeatherData> readFromFile(File file) throws IOException {
        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNext()) {
                String[] currentDataLine = scan.nextLine().split(",");
                NOAA.currentForecast.add(new NOAAWeatherData(Double.parseDouble(currentDataLine[0]),
                        Double.parseDouble(currentDataLine[1]),
                        Double.parseDouble(currentDataLine[2]),
                        Double.parseDouble(currentDataLine[3]),
                        Double.parseDouble(currentDataLine[4])));
            }

            scan.close();
            return NOAA.currentForecast;
        } catch (IOException e) {
            DefaultLogger.logger.error("Error reading forecast from file: " + file.getName());
            throw e;
        }
    }
    
    
    /**
    
     * Checks if the user can successfully connect to the NOAA API used by RocketPyAlpha.
     * @return true if the user can connect to the API, false otherwise.
     */
    public static boolean isAvailable() {
        try {
             URL url = new URL("https://nomads.ncep.noaa.gov/");
             URLConnection connection = url.openConnection();
             connection.connect();
          } catch (IOException e) {
             DefaultLogger.logger.error(e.getMessage());
             return false;
          }
        return true;
    }

    /**
     * Leaving this here as an example on how to call the method.
     * The input for the date (i.e what type of object getWeather() accepts) is subject to change.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    	Instant now = Instant.now();
    	Instant twoDaysBefore = now.minus(1, ChronoUnit.DAYS);
    	
    	
    	Calendar dayBefore = Calendar.getInstance();
    	dayBefore.setTime(new Date(twoDaysBefore.toEpochMilli()));
    	
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        

        int days = (int) Duration.between(dayBefore.toInstant(), calendar.toInstant()).toDays();
        System.out.println(days);
//
//        System.out.println(getWeather(41, 174, 0,
//                calendar));
    }
}
