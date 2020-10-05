package nz.ac.vuw.engr301.group9mcs.externaldata.weather;

import nz.ac.vuw.engr301.group9mcs.commons.PythonContext;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Will run the NOAA python script, parse the output and return 
 * NOAAWeatherData objects. 
 * @author Sai
 */
public class NOAA {
    /**
     * Calls the NOAA python script and parses the returned weather data into a sorted List.
     * @param latitude the latitude of the launch site
     * @param longitude the longitude of the launch site
     * @param daysAhead how far ahead (in days from the current time) the user would like their forecast
     * @param date a Date Time object of the date the user wants the forecast for. 
     * @return a Map with the weather data.
     * @throws IOException if the user does not have Python or the required modules (see PythonContext.java)
     */
    public static List<NOAAWeatherData> getWeather(double latitude, double longitude, int daysAhead, Calendar date) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String output = PythonContext.runNOAA(latitude, longitude, daysAhead, Integer.parseInt(sdf.format(date.getTime())));

        JSONArray array = new JSONArray(output);

        return getSortedList(array);
    }

    
    /** Converts the supplied JSON weather readings into a Sorted List.
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
     * Leaving this here as an example on how to call the method.
     * The input for the date (i.e what type of object getWeather() accepts) is subject to change.
     */
//    public static void main(String[] args) throws IOException {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//
//        System.out.println(getWeather(41, 174, 0,
//                calendar));
//    }
}
