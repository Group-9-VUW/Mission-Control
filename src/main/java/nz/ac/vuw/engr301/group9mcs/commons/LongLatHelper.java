/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * @author Claire
 */
public final class LongLatHelper {

	/**
	 * Returns how many kilometers exist between degrees of latitude (Distance between the poles)
	 * at the specific latitude given.
	 * 
	 * @param latitude The latitude you're measuring from
	 * @return The number of kilometers for every degree of latitude
	 */
	public static double kilometersPerDegreeOfLatitude(double latitude)
	{
		//TODO: implement ellipsoidal formulas
		return 110.567;
	}
	
	/**
	 * Returns how many kilometers exist between degrees of longditude (Distance between east and west)
	 * at the specific latitude given.
	 * 
	 * @param latitude The latitude you're measuring from
	 * @return The number of kilometers for every degree of longditude
	 */
	public static double kilometeresPerDegreeOfLongitude(double latitude)
	{
		return 111.3215 * Math.cos(Math.toRadians(Math.abs(latitude)));
	}
	
	public static double latitudeNKilometersNorth(double latitude, double N)
	{
		return latitude + (N / kilometersPerDegreeOfLatitude(latitude));
	}
	
	public static double latitudeNKilometersSouth(double latitude, double N)
	{
		return latitude - (N / kilometersPerDegreeOfLatitude(latitude));
	}
	
	public static double longditudeNKilometersEast(double latitude, double longitude, double N)
	{
		return longitude + (N / kilometeresPerDegreeOfLongitude(latitude));
	}
	
	public static double longditudeNKilometersWest(double latitude, double longitude, double N)
	{
		return longitude - (N / kilometeresPerDegreeOfLongitude(latitude));
	}
	
}
