package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * This class connects to the OpenWeatherMap one call API and retrieves weather data from it. 
 * The weather data is returned in the JSON format. 
 * The JSON will be parsed and the info will be pushed to all other packages that need it. 
 * @author Sai
 * 
 */
public class NOAAGetter {
	/*
	 * The appid for the OpenWeatherMap API. The user will supply their own token that will be used. 
	 */
	private String appid = "ead647e24776f26ed6f63af5f1bbf68c";
	
	
	/**
	 * Default constuctor for testing.
	 */
	public NOAAGetter() {
		System.out.println("Enter your appid for OpenWeatherMap:");
		Scanner scan = new Scanner(System.in);
		this.appid = scan.next();
		scan.close();
	}
	
	/**
	 * Gets the current weather at the supplied latitude and longitude
	 * @param latitude of the launch site
	 * @param longitude of the launch site
	 * @return a JSONObject with the current weather 
	 */
	public JSONObject getCurrentWeather(double latitude, double longitude) {
		try {
			String units = "metric";
			String urlString = "https://api.openweathermap.org/data/2.5/onecall?"
					+ "lat=" + latitude + "&lon=" + longitude + "&units=" + units + "&exclude=daily,hourly,minutely&appid=" + appid;
			
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			JSONObject weatherJSON = new JSONObject(reader.readLine());
			
			System.out.println(weatherJSON);
			
			JSONObject currentData = weatherJSON.getJSONObject("current");
			
			JSONObject rainData =  currentData.getJSONObject("rain");
			
			
			double temperature = currentData.getDouble("temp");
			
			// The units for the wind speed returned by the API is in meters per second. 
			// So we need to convert it to kilometers per hour as that is the standard 
			// unit of measurement for wind in New Zealand. 
			double windSpeed = ((currentData.getDouble("wind_speed") * 60) * 60) / 1000;
			
			double pressure = currentData.getDouble("pressure");
			
			// Amount of Rainfall in the last hour
			double precipitation = rainData.keySet().contains("1h") == true ? rainData.getDouble("1h") : 0.0;
			
			System.out.println(weatherJSON.toString());
			System.out.println("Temperature: " + temperature + "°C");
			System.out.println("Wind Speed: " + windSpeed + "km/h");
			System.out.println("Atmospheric Pressure (at sea level): " + pressure + "hPa");
			System.out.println("Precipitation (last hour): " + precipitation + "mm");
			reader.close();
			
			return weatherJSON;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Constructor for when the user supplies their API token
	 * @param token the token the user has supplied. 
	 */
	public NOAAGetter(String appid) {
		this.appid = appid;
	}
	
	/**
	 * @return the appid
	 */
	public String getAppId() {
		return appid;
	}
	/**
	 * @param appid the appid to set
	 */
	public void setAppId(String token) {
		this.appid = token;
	}



	public static void main(String[] args) {
		NOAAGetter getter = new NOAAGetter("ead647e24776f26ed6f63af5f1bbf68c");
		getter.getCurrentWeather(-41.289224, 174.768352);
	}
	
}
