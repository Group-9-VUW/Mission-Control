package nz.ac.vuw.engr301.group9mcs.commons.map;

/**
 * A helper class for dealing with long/lat coordinates
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public final class LongLatHelper {

	/**
	 * Returns how many kilometers exist between degrees of latitude (Distance between the poles)
	 * at the specific latitude given.
	 *
	 * @param latitude The latitude you're measuring from
	 * @return The number of kilometers for every degree of latitude
	 */
	public static double kilometersPerDegreeOfLatitude(double latitude) {
		return 110.567;
	}

	/**
	 * Returns how many kilometers exist between degrees of longitude (Distance between east and west)
	 * at the specific latitude given.
	 *
	 * @param latitude The latitude you're measuring from
	 * @return The number of kilometers for every degree of longitude
	 */
	public static double kilometeresPerDegreeOfLongitude(double latitude) {
		return 111.3215 * Math.cos(Math.toRadians(Math.abs(latitude)));
	}

	/**
	 * Returns the latitude N kilometers north of a given latitude.
	 * @param latitude The current latitude
	 * @param N The number of kilometers to go north
	 * @return The latitude N kilometers north
	 */
	public static double latitudeNKilometersNorth(double latitude, double N) {
		return latitude + (N / kilometersPerDegreeOfLatitude(latitude));
	}

	/**
	 * Returns the latitude N kilometers south of a given latitude.
	 * @param latitude The current latitude
	 * @param N The number of kilometers to go south
	 * @return The latitude N kilometers south
	 */
	public static double latitudeNKilometersSouth(double latitude, double N) {
		return latitude - (N / kilometersPerDegreeOfLatitude(latitude));
	}

	/**
	 * Returns the longitude N kilometers east of a given longitude.
	 * @param latitude The current latitude
	 * @param longitude The current longitude
	 * @param N The number of kilometers to go east
	 * @return The longitude N kilometers east
	 */
	public static double longitudeNKilometersEast(double latitude, double longitude, double N) {
		return longitude + (N / kilometeresPerDegreeOfLongitude(latitude));
	}

	/**
	 * Returns the longitude N kilometers west of a given longitude.
	 * @param latitude The current latitude
	 * @param longitude The current longitude
	 * @param N The number of kilometers to go west
	 * @return The longitude N kilometers west
	 */
	public static double longitudeNKilometersWest(double latitude, double longitude, double N) {
		return longitude - (N / kilometeresPerDegreeOfLongitude(latitude));
	}
}
