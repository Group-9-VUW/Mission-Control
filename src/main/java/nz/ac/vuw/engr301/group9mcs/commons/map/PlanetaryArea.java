package nz.ac.vuw.engr301.group9mcs.commons.map;

import org.eclipse.jdt.annotation.Nullable;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Condition;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;

/**
 * A class that represents an axis-aligned area of the planet,
 * coordinates are degrees of latitude and longitude.
 *
 * @author Claire Chambers
 * @editor Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class PlanetaryArea {

	/**
	 * The center latitude.
	 */
	private final double lat;

	/**
	 * The center longitude.
	 */
	private final double lon;

	/**
	 * Degrees of latitude from center to top and bottom edges.
	 */
	private final double radLat;

	/**
	 * Degrees of longitude from center to left and right edges.
	 */
	private final double radLon;

	/**
	 * Creates a new PlanetaryArea using the center latitude and longitude and the radius of the area.
	 * @param lat The center latitude of the area
	 * @param lon The center longitude of the area
	 * @param radLat The radius of this area in the latitudinal direction
	 * @param radLon The radius of this area in the longitudinal direction
	 */
	private PlanetaryArea(double lat, double lon, double radLat, double radLon) {
		this.lat = lat;
		this.lon = lon;
		this.radLat = radLat;
		this.radLon = radLon;
	}

	/**
	 * Returns a new PlanetaryArea scaled with the given factor.
	 * Scaling is done from the center of the area.
	 *
	 * @param val The scale factor, where 1.0 is no scaling
	 * @return The scaled PlanetaryArea object
	 */
	public PlanetaryArea scale(double val) {
		return new PlanetaryArea(this.lat, this.lon, this.radLat * val, this.radLon * val);
	}

	/**
	 * Clips a given Area to be inside this area.
	 * @param toClip Area to clip
	 * @return The clipped area
	 */
	public PlanetaryArea clip(PlanetaryArea toClip) {
		PlanetaryArea newArea = fromCorners(Math.min(this.getUpperLeftLatitude(), toClip.getUpperLeftLatitude()),
											Math.max(this.getUpperLeftLongitude(), toClip.getUpperLeftLongitude()),
											Math.max(this.getBottomRightLatitude(), toClip.getBottomRightLatitude()),
											Math.min(this.getBottomRightLongitude(), toClip.getBottomRightLongitude()));
		if(!this.containsArea(newArea))
			throw new PostconditionViolationException("Algorithm didn't clip properly");
		return newArea;
	}

	/**
	 * Checks whether an area is entirely contained by this one.
	 * @param area The area to be checked
	 * @return Whether the given area is entirely contained within this one
	 */
	public boolean containsArea(PlanetaryArea area) {
			//top left corner
		return this.containsPoint(area.getUpperLeftLatitude(), area.getUpperLeftLongitude())
			//top right corner
			&& this.containsPoint(area.getUpperLeftLatitude(), area.getBottomRightLongitude())
			//bottom right corner
			&& this.containsPoint(area.getBottomRightLatitude(), area.getBottomRightLongitude())
			//bottom left corner
			&& this.containsPoint(area.getBottomRightLatitude(), area.getUpperLeftLongitude());
	}

	/**
	 * Checks whether an area overlaps with this one.
	 * @param area The area to check
	 * @return Whether the given area overlaps with this one
	 */
	public boolean overlapsWithArea(PlanetaryArea area) {
		return this.containsPoint(area.getUpperLeftLatitude(), area.getUpperLeftLongitude())
			|| this.containsPoint(area.getUpperLeftLatitude(), area.getBottomRightLongitude())
			|| this.containsPoint(area.getBottomRightLatitude(), area.getBottomRightLongitude())
			|| this.containsPoint(area.getBottomRightLatitude(), area.getUpperLeftLongitude())
			|| area.containsPoint(this.getUpperLeftLatitude(), this.getUpperLeftLongitude())
			|| area.containsPoint(this.getUpperLeftLatitude(), this.getBottomRightLongitude())
			|| area.containsPoint(this.getBottomRightLatitude(), this.getBottomRightLongitude())
			|| area.containsPoint(this.getBottomRightLatitude(), this.getUpperLeftLongitude());
	}


	/**
	 * Determines whether this planetary area contains a given point.
	 * @param latitude The latitude of the point
	 * @param longitude The longitude of the point
	 * @return Whether the point is contained in this area
	 */
	public boolean containsPoint(double latitude, double longitude) {
		return (Math.abs(this.lat - latitude) - this.radLat)  < 0.000001
				&& (Math.abs(this.lon - longitude) - this.radLon) < 0.000001;
	}

	/**
	 * @return the center latitude
	 */
	public double getLat() {
		return this.lat;
	}

	/**
	 * @return the center longitude
	 */
	public double getLon() {
		return this.lon;
	}

	/**
	 * @return the radius of the latitudes
	 */
	public double getRadLat() {
		return this.radLat;
	}

	/**
	 * @return the radius of the longitudes
	 */
	public double getRadLon() {
		return this.radLon;
	}

	/**
	 * @return The upper left latitude
	 */
	public double getUpperLeftLatitude() {
		return this.lat + this.radLat;
	}

	/**
	 * @return The bottom right latitude
	 */
	public double getBottomRightLatitude() {
		return this.lat - this.radLat;
	}

	/**
	 * @return The upper left longitude
	 */
	public double getUpperLeftLongitude() {
		return this.lon - this.radLon;
	}

	/**
	 * @return The bottom right longitude
	 */
	public double getBottomRightLongitude() {
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
	 * Creates a PlanetaryArea object from two corner coordinates.
	 *
	 * @param latUL Latitude of upper left corner
	 * @param lonUL Longitude of upper left corner
	 * @param latBR Latitude of bottom right corner
	 * @param lonBR Longitude of bottom right corner
	 * @return A PlanetaryArea object with this data
	 */
	public static final PlanetaryArea fromCorners(double latUL, double lonUL, double latBR, double lonBR) {
		if(latUL < latBR) {
			throw new PreconditionViolationException("Invalid planetary area specification, upper latitude should be larger than lower latitude");
		}
		if(lonUL > lonBR) {
			throw new PreconditionViolationException("Invalid planetary area specification, left longitude should be smaller than right longitude");
		}
		double latCenter = (latUL + latBR) / 2;
		double lonCenter = (lonUL + lonBR) / 2;
		double latRadius = Math.abs(latUL - latBR) / 2;
		double lonRadius = Math.abs(lonUL - lonBR) / 2;
		return new PlanetaryArea(latCenter, lonCenter, latRadius, lonRadius);
	}

	/**
	 * Creates a PlanetaryArea object from a center coordinate and a X,Y radius.
	 *
	 * @param latCenter Center latitude
	 * @param lonCenter Center longitude
	 * @param latRadius Radius, latitude-wise
	 * @param lonRadius Radius, longitude-wise
	 * @return A PlanetaryArea object with this data
	 */
	public static final PlanetaryArea fromCenter(double latCenter, double lonCenter, double latRadius, double lonRadius) {
		Condition.PRE.positive("latRadius", (int) Math.ceil(latRadius));
		Condition.PRE.positive("lonRadius", (int) Math.ceil(lonRadius));
		return new PlanetaryArea(latCenter, lonCenter, latRadius, lonRadius);
	}
}
