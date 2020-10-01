package nz.ac.vuw.engr301.group9mcs.commons.map;

import org.eclipse.jdt.annotation.Nullable;

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

	@Override
	public boolean equals(@Nullable Object other) {
		if (other == null) {
			return false;
		} else if (!(other instanceof Point)) {
			return false;
		}
		Point o = (Point) other;
		return this.latitude == o.latitude
				&& this.longitude == o.longitude;
	}

	@Override
	public int hashCode() {
		return (int)(31 * this.latitude + this.longitude);
	}

}
