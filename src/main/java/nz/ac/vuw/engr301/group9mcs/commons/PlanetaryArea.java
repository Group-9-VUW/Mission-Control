/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.commons;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A class that represents an axis-aligned area of the planet,
 * coordinates are degrees of latitude and longitude.
 * 
 * @author Claire
 */
public class PlanetaryArea {
	
	private final double lat;
	private final double lon;
	private final double radLat;
	private final double radLon;
	
	private PlanetaryArea(double lat, double lon, double radLat, double radLon)
	{
		this.lat = lat;
		this.lon = lon;
		this.radLat = radLat;
		this.radLon = radLon;
	}
	
	/**
	 * Returns a new PlanetaryArea scaled with the given factor.
	 * Scaling is done from the center of the area 
	 * 
	 * @param val The scale factor, where 1.0 is no scaling
	 * @return The new PlanetaryArea object
	 */
	public PlanetaryArea scale(double val)
	{
		return new PlanetaryArea(this.lat, this.lon, this.radLat * val, this.radLon * val);
	}
	
	/**
	 * @return the lat
	 */
	public double getLat() 
	{
		return this.lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() 
	{
		return this.lon;
	}

	/**
	 * @return the radLat
	 */
	public double getRadLat() 
	{
		return this.radLat;
	}

	/**
	 * @return the radLon
	 */
	public double getRadLon() 
	{
		return this.radLon;
	}
	
	/**
	 * @return The upper left latitude
	 */
	public double getUpperLeftLatitude()
	{
		return this.lat + this.radLat;
	}
	
	/**
	 * @return The upper left latitude
	 */
	public double geBottomRightLatitude()
	{
		return this.lat - this.radLat;
	}
	
	/**
	 * @return The upper left longitude
	 */
	public double getUpperLeftLongitude()
	{
		return this.lon - this.radLon;
	}
	
	/**
	 * @return The bottom right longitude
	 */
	public double geBottomRightLongitude()
	{
		return this.lon + this.radLon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.radLat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.radLon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlanetaryArea other = (PlanetaryArea) obj;
		if (Double.doubleToLongBits(this.lat) != Double.doubleToLongBits(other.lat)) {
			return false;
		}
		if (Double.doubleToLongBits(this.lon) != Double.doubleToLongBits(other.lon)) {
			return false;
		}
		if (Double.doubleToLongBits(this.radLat) != Double.doubleToLongBits(other.radLat)) {
			return false;
		}
		if (Double.doubleToLongBits(this.radLon) != Double.doubleToLongBits(other.radLon)) {
			return false;
		}
		return true;
	}

	/**
	 * Creates a PlanetaryArea object from two corner coordinates
	 * 
	 * @param latUL Latitude of upper left corner
	 * @param lonUL Longitude of upper left corner
	 * @param latBR Latitude of bottom right corner
	 * @param lonBR Longitude of bottom right corner
	 * @return A PlanetaryArea object with this data
	 */
	public static final PlanetaryArea fromCorners(double latUL, double lonUL, double latBR, double lonBR)
	{
		double latCenter = (latUL + latBR) / 2;
		double lonCenter = (lonUL + lonBR) / 2;
		double latRadius = Math.abs(latUL - latBR) / 2;
		double lonRadius = Math.abs(lonUL - lonBR) / 2;
		return new PlanetaryArea(latCenter, lonCenter, latRadius, lonRadius);
	}
	
	/**
	 * Creates a PlanetaryArea object from a center coordinate and a X,Y radius
	 * 
	 * @param latCenter Center latitude
	 * @param lonCenter Center longitude
	 * @param latRadius Radius, latitude-wise
	 * @param lonRadius Radius, longitude-wise
	 * @return A PlanetaryArea object with this data
	 */
	public static final PlanetaryArea fromCenter(double latCenter, double lonCenter, double latRadius, double lonRadius)
	{
		return new PlanetaryArea(latCenter, lonCenter, latRadius, lonRadius);
	}

}
