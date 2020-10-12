package nz.ac.vuw.engr301.group9mcs.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.commons.map.PlanetaryArea;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.CachedOsmOverpassData;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.InternetMapImage;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.OsmOverpassData;
import nz.ac.vuw.engr301.group9mcs.externaldata.map.OsmOverpassGetter;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAA;
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
	
	/**
	 * Saves a launch to file
	 * 
	 * @param rocket
	 * @param rod
	 * @param data
	 * @param area 
	 * 
	 * @throws IOException 
	 */
	public static final void saveLaunch(File rocket, LaunchRodData rod, List<NOAAWeatherData> data, PlanetaryArea area) throws IOException
	{
		Files.copy(rocket.toPath(), new File(ROOT_DIR + "rocket.ork").toPath());
		
		File rodData = new File(ROOT_DIR + "rod.dat");
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rodData))) {
			oos.writeObject(rodData);
		}
		
		NOAA.writeToFile(new File(ROOT_DIR + "weather.dat"), data);
		
		OsmOverpassData osmData = OsmOverpassGetter.getAreasInBox(area.getUpperLeftLatitude(), area.getUpperLeftLongitude(), area.getBottomRightLatitude(), area.getBottomRightLongitude());
		CachedOsmOverpassData.saveArea(new Point(area.getUpperLeftLatitude(), area.getUpperLeftLongitude()), new Point(area.getBottomRightLatitude(), area.getBottomRightLongitude()), osmData);
		
		File image = new File(ROOT_DIR + "image.png");
		@SuppressWarnings("unused")
		CachedMapImage ci = new CachedMapImage(new InternetMapImage(), area.getUpperLeftLatitude(), area.getUpperLeftLongitude(), area.getBottomRightLatitude(), area.getBottomRightLongitude(), image);
	}

}
