package nz.ac.vuw.engr301.group9mcs.commons.map;

/**
 * @author Claire
 */
public class Point {
	
	/**
	 * The latitude of the point
	 */
	private final double latitude;
	
	/**
	 * The longitude of the point
	 */
	private final double longitude;

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}

}
