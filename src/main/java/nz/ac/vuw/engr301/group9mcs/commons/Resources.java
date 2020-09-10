package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds resources needed to pass to Perspectives
 * 
 * @author Bryony
 *
 */
public class Resources {

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
	
}
