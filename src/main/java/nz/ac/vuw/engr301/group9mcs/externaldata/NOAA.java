package nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.NOAAWeatherData;
import nz.ac.vuw.engr301.group9mcs.commons.PythonContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Will run the NOAA python script, parse the output and return 
 * NOAAWeatherData objects. 
 * @author Sai
 */
public class NOAA {
    /**
     * Calls the NOAA python script and parses the returned weather data into a Map.
     * The keys for the map are the altitudes and the values are NOAAWeatherData objects.
     * @param latitude the latitude of the launch site
     * @param longitude the longitude of the launch site
     * @param daysAhead how far ahead (in days from the current time) the user would like their forecase
     * @return a Map with the weather data.
     * @throws IOException if the user does not have Python or the required modules (see PythonContext.java)
     */
    public static Map<Double, NOAAWeatherData> getWeather(double latitude, double longitude, int daysAhead) throws IOException {
        String output = PythonContext.runNOAA(latitude, longitude, daysAhead);

        JSONArray array = new JSONArray(output);

        return convertToMap(array);
    }

    
    /** Converts the supplied JSON weather readings into a Map
     * @param jsonWeatherReadings the weather readings in JSON format 
     * @return a map with the same data as jsonWeatherReadings, where the keys are the alitudes and values are NOAAWeatherData objects.  
     */
    public static Map<Double, NOAAWeatherData> convertToMap(JSONArray jsonWeatherReadings) {
        Map<Double, NOAAWeatherData> weatherReadings = new HashMap<>();

        for (int i = 0; i < jsonWeatherReadings.length(); i++) {
            JSONObject currentReading = jsonWeatherReadings.getJSONObject(i);
            weatherReadings.put(new Double(currentReading.getDouble("altitude")),
                    new NOAAWeatherData(currentReading.getDouble("windSpeed"),
                            currentReading.getDouble("windDirection"),
                            currentReading.getDouble("temperature"),
                            currentReading.getDouble("pressure")));
        }

        return weatherReadings;
    }

}
