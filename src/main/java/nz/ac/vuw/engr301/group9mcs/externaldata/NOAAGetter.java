package nz.ac.vuw.engr301.group9mcs.externaldata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * This class connects to the NOAA (National Oceanic and Atmospheric Administration) API and retrieves weather data from it. 
 * The weather data is returned in the JSON format. 
 * The JSON will be parsed and the info will be pushed to all other packages that need it. 
 * @author Sai
 * 
 */
public class NOAAGetter {
	/*
	 * The token for the NOAA API. The user will supply their own token that will be used. 
	 */
	//private String token = "RmODwrZcMuHzlQlnhhzwVeQplsefwlVD";
	//private String cityID = "CITY:NZ000007"; // "CITY:NZ000007" is the cityID for wellington (NOAA API)
	
	/*
	 * The token for the OpenWeatherMap API. The user will supply their own token that will be used. 
	 */
	private String appid = "ead647e24776f26ed6f63af5f1bbf68c";
	
	
	private double latitude = -41.289224; 
	private double longitude = 174.768352;

	
	/**
	 * Default constuctor for testing.
	 */
	public NOAAGetter() {
		getCurrentWeather();
	}
	
	/**
	 * Gets the current weather data. 
	 * @return a JSONObject containing the current weather data. 
	 */
	public JSONObject getCurrentWeather() {
		try {
			String units = "metric";
			String urlString = "https://api.openweathermap.org/data/2.5/onecall?"
					+ "lat=" + latitude + "&lon=" + longitude + "&units=" + units + "&exclude=daily,hourly,minutely&appid=" + appid;
			
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			JSONObject weatherJSON = new JSONObject(reader.readLine());
			
			JSONObject currentData =  (JSONObject) weatherJSON.get("current");
			JSONObject rainData = (JSONObject) currentData.get("rain");
			
			double temperature = currentData.getDouble("temp");
			
			// The units for the wind speed returned by the API is in meters per second. 
			// So we need to convert it to kilometers per hour as that is the standard 
			// unit of measurement for wind in New Zealand. 
			double windSpeed = ((currentData.getDouble("wind_speed") * 60) * 60) / 1000;
			
			double pressure = currentData.getDouble("pressure");
			
			// Amount of Rainfall in the last hour
			double precipitation = rainData.getDouble("1h");
			
			System.out.println(weatherJSON.toString());
			System.out.println("Temperature: " + temperature + "°C");
			System.out.println("Wind Speed: " + windSpeed + "km/h");
			System.out.println("Atmospheric Pressure (at sea level): " + pressure + "hPa");
			System.out.println("Precipitation (last hour): " + precipitation + "mm");
			reader.close();
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
	public NOAAGetter(String token) {
		this.appid = token;
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return appid;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.appid = token;
	}


	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public static void main(String[] args) {
		new NOAAGetter();
	}
	
}
