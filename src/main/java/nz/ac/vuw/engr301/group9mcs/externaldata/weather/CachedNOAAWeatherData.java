package nz.ac.vuw.engr301.group9mcs.externaldata.weather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;

import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;

/**
 * A class to save and load the NOAAWeather data to a JSON file.
 * 
 * @author Joshua Hindley
 */
public class CachedNOAAWeatherData {

	/**
	 * The weather data to save or load as a JSON array.
	 */
	private JSONArray weatherData = new JSONArray();

	/**
	 * The name of the file that weatherData should be saved in.
	 */
	public static final String fileName = "cached_data/weatherData.json";

	/**
	 * Saves a weather data JSONArray to a file.
	 * @param data the weather data to cache, as a JSONArray.
	 * @throws IOException if there is a problem saving the JSONArray to a file.
	 * @throws JSONException if the provided JSONArray is not syntactically correct.
	 */
	public CachedNOAAWeatherData(JSONArray data) throws IOException, JSONException {
		this.weatherData = data;
		saveData();
	}

	/**
	 * Creates a new CachedNOAAWeatherData instance to load the NOAAWeatherData.
	 * @throws FileNotFoundException if the file is not found.
	 * @throws JSONException if the JSON in the file is not syntactically correct.
	 */
	public CachedNOAAWeatherData() throws FileNotFoundException, JSONException {
		loadData();
	}

	/**
	 * Saves the weather data to the designated file.
	 * @throws IOException if there is a problem saving the JSONArray to a file.
	 * @throws JSONException if the provided JSONArray is not syntactically correct.
	 */
	private void saveData() throws IOException, JSONException {
		try {
			//tests that the provided weatherData is valid
			if (this.weatherData.length() == 0 || this.weatherData.toString() == null) {
				throw new JSONException("The provided JSON array is empty or not syntactically correct.");
			}
			//gets the name of the folder and creates it if it doesn't exist
			File folder = new File(fileName.split("/")[0]);
			if (!folder.exists()) folder.mkdir();
			File jsonFile = new File(fileName);
			//creates the file if it does not exist
			jsonFile.createNewFile();
			try (BufferedWriter out = new BufferedWriter(new FileWriter(jsonFile));) {
				out.write(this.weatherData.toString());
			}} catch (IOException | JSONException e) {
				DefaultLogger.logger.error(e.getMessage());
				throw e;
			}
	}

	/**
	 * Loads the cached weather data from the file, and converts it to a JSONArray.
	 * @throws FileNotFoundException if the file is not found.
	 * @throws JSONException if the JSON in the file is not syntactically correct.
	 */
	private void loadData() throws FileNotFoundException, JSONException {
		//loads the weather data from the file and converts to a JSONArray
		try (Scanner sc = new Scanner(new File(fileName));) {
			this.weatherData = new JSONArray(sc.nextLine());
		} catch (FileNotFoundException | JSONException e) {
			DefaultLogger.logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Gets the weather data JSONArray that was saved or loaded by this instance.
	 * @return the weather data as a JSONArray.
	 */
	public JSONArray getData() {
		return this.weatherData;
	}
}
