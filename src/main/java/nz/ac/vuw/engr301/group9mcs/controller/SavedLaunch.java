package nz.ac.vuw.engr301.group9mcs.controller;

import java.io.File;
import java.util.List;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAAWeatherData;

/**
 * A class that represents a saved launch.
 * 
 * @author Claire
 */
public class SavedLaunch {

	/**
	 * The root directory for saved launches
	 */
	private static final String ROOT_DIR = "saved_launch/";
	
	static {
		new File(ROOT_DIR).mkdirs();
	}
	
	/**
	 * The cached map image for this launch
	 */
	private final CachedMapImage image;
	
	/**
	 * The cached weather data for this launch
	 */
	private final List<NOAAWeatherData> data;
	
	/**
	 * The launch rod data
	 */
	private final LaunchRodData rod;
	
	/**
	 * A .ork for the rocket being launched
	 */
	private final File rocketData;

	/**
	 * @param image
	 * @param data
	 * @param rod
	 * @param rocketData
	 */
	public SavedLaunch(CachedMapImage image, List<NOAAWeatherData> data, LaunchRodData rod, File rocketData) {
		this.image = image;
		this.data = data;
		this.rod = rod;
		this.rocketData = rocketData;
	}

	/**
	 * @return the image
	 */
	public CachedMapImage getImage() {
		return this.image;
	}

	/**
	 * @return the data
	 */
	public List<NOAAWeatherData> getData() {
		return this.data;
	}

	/**
	 * @return the rod
	 */
	public LaunchRodData getRod() {
		return this.rod;
	}

	/**
	 * @return the rocketData
	 */
	public File getRocketData() {
		return this.rocketData;
	}

}
