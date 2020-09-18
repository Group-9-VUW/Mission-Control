package nz.ac.vuw.engr301.group9mcs.controller;

import javax.swing.JFrame;

import nz.ac.vuw.engr301.group9mcs.avionics.LORADriver;

/**
 * Holds resources needed to pass to Perspectives
 * 
 * @author Bryony
 *
 */
public class Resources {

	/**
	 * The root JFrame
	 */
	private final JFrame frame;
	
	/**
	 * The LoRA driver
	 */
	private final LORADriver driver;
	
	/**
	 * The Longitude (launch site?)
	 */
	private double longitude;
	/**
	 * The Latitude (launch site?)
	 */
	private double latitude;
	/**
	 * TODO: simulation connection?
	 * pass filename, weather data?
	 */
	/**
	 * TODO: weather data
	 * Internet version
	 * parse user input?
	 */
	/**
	 * TODO: map image?
	 * cache/Internet
	 */
	/**
	 * TODO: rocket connection
	 */
	/**
	 * TODO: rocket data
	 */
	
	/**
	 * @param frame The root JFrame
	 */
	public Resources(JFrame frame)
	{
		this.frame = frame;
		this.driver = new LORADriver();
	}
	
	/**
	 * Returns the Longitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * 
	 * @return Returns a Double
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Sets the Longitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * Not sure if it's needed
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Returns the Latitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * 
	 * @return Returns a Double
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Sets the Latitude (typically launch site position).
	 * Used for SelectSitePerspective
	 * Not sure if it's needed
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() 
	{
		return this.frame;
	}

	/**
	 * @return the driver
	 */
	public LORADriver getDriver() 
	{
		return this.driver;
	}
	
}
