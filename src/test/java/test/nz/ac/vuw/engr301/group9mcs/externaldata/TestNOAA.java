package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAA;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAWeatherData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the NOAA class for correctness of the data produced by it (the NOAA weather readings parsed into a List).
 * @author Sai
 */
public class TestNOAA {
    /**
     * Sample output from the NOAA script.
     */
    private final JSONArray testArray = new JSONArray("[{'altitude': 228.1233367919922, 'windSpeed': 3.4823615550994873, " +
            "'windDirection': 85.77337646484375, 'temperature': 287.0677185058594, 'pressure': 100000.0}, " +
            "{'altitude': 440.8855285644531, 'windSpeed': 3.5565133094787598, 'windDirection': 86.97354125976562, " +
            "'temperature': 285.0450744628906, 'pressure': 97500.0}, {'altitude': 657.5938110351562, 'windSpeed': 3" +
            ".6175389289855957, 'windDirection': 88.5770263671875, 'temperature': 282.8999938964844, 'pressure': " +
            "95000.0}, {'altitude': 878.4491577148438, 'windSpeed': 3.672999620437622, 'windDirection': 90" +
            ".28524780273438, 'temperature': 280.79998779296875, 'pressure': 92500.0}, {'altitude': 1103" +
            ".5794677734375, 'windSpeed': 3.6549618244171143, 'windDirection': 91.9346923828125, 'temperature': 278" +
            ".70001220703125, 'pressure': 90000.0}, {'altitude': 1572.539306640625, 'windSpeed': 3.50815749168396, " +
            "'windDirection': 91.84921264648438, 'temperature': 281.3000183105469, 'pressure': 85000.0}, {'altitude':" +
            " 2071.9365234375, 'windSpeed': 2.6866466999053955, 'windDirection': 90.59063720703125, 'temperature': " +
            "280.79998779296875, 'pressure': 80000.0}, {'altitude': 2602.09912109375, 'windSpeed': 0" +
            ".9904838800430298, 'windDirection': 54.64289855957031, 'temperature': 279.688232421875, 'pressure': " +
            "75000.0}, {'altitude': 3164.79052734375, 'windSpeed': 2.1333765983581543, 'windDirection': 325" +
            ".2865295410156, 'temperature': 276.8999938964844, 'pressure': 70000.0}, {'altitude': 3763.09326171875, " +
            "'windSpeed': 3.207829475402832, 'windDirection': 296.0469970703125, 'temperature': 274.0218200683594, " +
            "'pressure': 65000.0}, {'altitude': 4402.09619140625, 'windSpeed': 4.683414936065674, 'windDirection': " +
            "284.9971618652344, 'temperature': 270.4717712402344, 'pressure': 60000.0}, {'altitude': 5086" +
            ".63818359375, 'windSpeed': 6.767282485961914, 'windDirection': 285.3511657714844, 'temperature': 266" +
            ".1999816894531, 'pressure': 55000.0}, {'altitude': 5823.9296875, 'windSpeed': 8.060235977172852, " +
            "'windDirection': 279.5382080078125, 'temperature': 261.3999938964844, 'pressure': 50000.0}, {'altitude':" +
            " 6622.9033203125, 'windSpeed': 10.20433521270752, 'windDirection': 280.2786865234375, 'temperature': 255" +
            ".5999755859375, 'pressure': 45000.0}, {'altitude': 7495.04052734375, 'windSpeed': 13.231345176696777, " +
            "'windDirection': 281.4873046875, 'temperature': 249.5, 'pressure': 40000.0}, {'altitude': 8456" +
            ".3994140625, 'windSpeed': 16.210758209228516, 'windDirection': 278.05889892578125, 'temperature': 241" +
            ".7999725341797, 'pressure': 35000.0}, {'altitude': 9532.9404296875, 'windSpeed': 18.397628784179688, " +
            "'windDirection': 278.55035400390625, 'temperature': 233.8639678955078, 'pressure': 30000.0}, " +
            "{'altitude': 10762.251953125, 'windSpeed': 22.98289680480957, 'windDirection': 276.93756103515625, " +
            "'temperature': 224.4969482421875, 'pressure': 25000.0}, {'altitude': 12216.8603515625, 'windSpeed': 28" +
            ".442956924438477, 'windDirection': 282.07208251953125, 'temperature': 219.8999786376953, 'pressure': " +
            "20000.0}, {'altitude': 14050.7177734375, 'windSpeed': 26.802278518676758, 'windDirection': 281" +
            ".3111572265625, 'temperature': 213.90234375, 'pressure': 15000.0}, {'altitude': 16567.611328125, " +
            "'windSpeed': 18.856069564819336, 'windDirection': 278.5646057128906, 'temperature': 209.40000915527344, " +
            "'pressure': 10000.0}, {'altitude': 18767.291015625, 'windSpeed': 12.243927001953125, 'windDirection': " +
            "282.55908203125, 'temperature': 210.3332061767578, 'pressure': 7000.0}, {'altitude': 20874.7578125, " +
            "'windSpeed': 5.442160129547119, 'windDirection': 264.73016357421875, 'temperature': 215.1874237060547, " +
            "'pressure': 5000.0}, {'altitude': 24156.044921875, 'windSpeed': 2.2648117542266846, 'windDirection': 267" +
            ".934814453125, 'temperature': 220.2410430908203, 'pressure': 3000.0}, {'altitude': 26809.779296875, " +
            "'windSpeed': 2.924549102783203, 'windDirection': 273.78851318359375, 'temperature': 223.0, 'pressure': " +
            "2000.0}, {'altitude': 31398.142578125, 'windSpeed': 0.779919445514679, 'windDirection': 270" +
            ".0125732421875, 'temperature': 226.3999786376953, 'pressure': 1000.0}, {'altitude': 33811.1171875, " +
            "'windSpeed': 1.2223111391067505, 'windDirection': 102.47540283203125, 'temperature': 231.1999969482422, " +
            "'pressure': 700.0}, {'altitude': 36138.734375, 'windSpeed': 1.030625581741333, 'windDirection': 44" +
            ".53572082519531, 'temperature': 236.69998168945312, 'pressure': 500.0}, {'altitude': 39803.328125, " +
            "'windSpeed': 1.2930033206939697, 'windDirection': 10.74688720703125, 'temperature': 247.05191040039062, " +
            "'pressure': 300.0}, {'altitude': 42821.99609375, 'windSpeed': 2.225761651992798, 'windDirection': 336" +
            ".1505126953125, 'temperature': 255.0636749267578, 'pressure': 200.0}, {'altitude': 48156.625, " +
            "'windSpeed': 1.5089271068572998, 'windDirection': 173.79226684570312, 'temperature': 261.6031799316406, " +
            "'pressure': 100.0}]");


    /**
     * Checks if each entry in the list has an altitude smaller than the next entry.
     */
    @Test
    public void checkIsSorted() {
        List<NOAAWeatherData> forecast = NOAA.getSortedList(Null.nonNull(this.testArray));

        for (int i = 0; i < forecast.size() - 1; i++) {
            assertTrue(forecast.get(i).getAltitude() < forecast.get(i+1).getAltitude());
        }
    }

    /**
     * Check the data correctly matches up with the corresponding altitude value.
     */
    @Test
    public void checkDataCorrectness() {

        List<NOAAWeatherData> forecast = NOAA.getSortedList(Null.nonNull(this.testArray));

        for (int i = 0; i < this.testArray.length(); i++) {
            JSONObject currentReading = this.testArray.getJSONObject(i);
            NOAAWeatherData currentWeatherData = forecast.get(0);

            for (NOAAWeatherData data : forecast) {
                if (data.getAltitude() == currentReading.getDouble("altitude")) {
                    currentWeatherData = data;
                    break;
                }
            }

            assertEquals(currentReading.getDouble("windSpeed"),
                    currentWeatherData.getWindSpeed());

            assertEquals(currentReading.getDouble("windDirection"),
                    currentWeatherData.getWindDirection());

            assertEquals(currentReading.getDouble("temperature"),
                    currentWeatherData.getTemperature());

            assertEquals(currentReading.getDouble("pressure"),
                    currentWeatherData.getPressure());

            forecast.remove(currentWeatherData); // Remove it from the list as it has already ben checked.
        }
    }
}
