package nz.ac.vuw.engr301.group9mcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
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
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
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
	 * The launch location
	 */
	private final Point launchSite;

	/**
	 * A .ork for the rocket being launched
	 */
	private final File rocketData;

	/**
	 * @param image
	 * @param data
	 * @param rod
	 * @param launchSite
	 * @param rocketData
	 */
	public SavedLaunch(CachedMapImage image, List<NOAAWeatherData> data, LaunchRodData rod, Point launchSite, File rocketData) {
		this.image = image;
		this.data = data;
		this.rod = rod;
		this.launchSite = launchSite;
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
	 * @param launchSite
	 * @param rod
	 * @param data
	 * @param area
	 *
	 * @throws IOException
	 */
	public static final void saveLaunch(File rocket, Point launchSite, LaunchRodData rod, List<NOAAWeatherData> data, PlanetaryArea area) throws IOException
	{
		new File(ROOT_DIR + "rocket.ork").delete();
		Files.copy(rocket.toPath(), new File(ROOT_DIR + "rocket.ork").toPath());

		File rodData = new File(ROOT_DIR + "rod.dat");
		rodData.createNewFile();
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rodData))) {
			oos.writeObject(launchSite);
			oos.writeObject(rod);
		}

		new File(ROOT_DIR + "weather.dat").delete();
		NOAA.writeToFile(new File(ROOT_DIR + "weather.dat"), data);

		OsmOverpassData osmData = OsmOverpassGetter.getAreasInBox(area.getUpperLeftLatitude(), area.getUpperLeftLongitude(), area.getBottomRightLatitude(), area.getBottomRightLongitude());
		CachedOsmOverpassData.saveArea(new Point(area.getUpperLeftLatitude(), area.getUpperLeftLongitude()), new Point(area.getBottomRightLatitude(), area.getBottomRightLongitude()), osmData);

		File image = new File(ROOT_DIR + "image.png");
		@SuppressWarnings("unused")
		CachedMapImage ci = new CachedMapImage(new InternetMapImage(), area.getUpperLeftLatitude(), area.getUpperLeftLongitude(), area.getBottomRightLatitude(), area.getBottomRightLongitude(), image);
	}

	/**
	 * @return Whether or not a launch exists
	 */
	public static final boolean hasLaunch()
	{
		return new File(ROOT_DIR + "rod.dat").exists() && new File(ROOT_DIR + "rod.dat").isFile();
	}

	/**
	 * @return the launchSite
	 */
	public Point getLaunchSite() {
		return this.launchSite;
	}

	/**
	 * Requires <code>hasLaunch() == true</code>
	 * @return The saved launch stored
	 * @throws Exception If any error occurs when reading from a file
	 */
	public static final SavedLaunch loadLaunch() throws Exception
	{
		if(!hasLaunch()) {
			throw new PreconditionViolationException("Launch doesn't exist.");
		}

		File rocket = new File(ROOT_DIR + "rocket.ork");
		if(!(rocket.exists() && rocket.isFile())) {
			throw new PreconditionViolationException("Rocket.ork doesn't exist");
		}

		File rodData = new File(ROOT_DIR + "rod.dat");
		if(!(rodData.exists() && rodData.isFile())) {
			throw new PreconditionViolationException("Rod data doesn't exist");
		}
		LaunchRodData data = null;
		Point launchSite = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rodData))) {
			launchSite = (Point) ois.readObject();
			data = (LaunchRodData) ois.readObject();
		}

		File weather = new File(ROOT_DIR + "weather.dat");
		if(!(weather.exists() && weather.isFile())) {
			throw new PreconditionViolationException("weather data doesn't exist");
		}
		List<NOAAWeatherData> weatherData = NOAA.readFromFile(weather);

		File image = new File(ROOT_DIR + "image.png");
		if(!(image.exists() && image.isFile())) {
			throw new PreconditionViolationException("image data doesn't exist");
		}
		CachedMapImage cimage = new CachedMapImage(image);

		return new SavedLaunch(cimage,  weatherData, Null.nonNull(data), Null.nonNull(launchSite), rocket);
	}

}
