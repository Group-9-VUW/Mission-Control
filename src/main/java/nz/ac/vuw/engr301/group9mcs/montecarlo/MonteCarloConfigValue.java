/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.util.function.Predicate;

/**
 * An enumeration for all the configurable values in the Monte Carlo program
 * 
 * @author Claire
 */
public enum MonteCarloConfigValue {
	
	/**
	 * The launch rod angle, with zero being directly upwards
	 * TODO: Degrees or radians
	 */
	LAUNCH_ROD_ANGLE("launchRodAngle", (d) -> { return true; }),
	
	/**
	 * The length of the launch rod, in meters
	 */
	LAUNCH_ROD_HEIGHT("launchRodLength", (d) -> { return d > 0; }),
	
	/**
	 * The direction of the launch rod
	 * TODO: Units
	 */
	LAUNCH_ROD_DIRECTION("launchRodDir", (d) -> { return true; }),
	
	/**
	 * The latitude of the launch site
	 */
	LAUNCH_LATITUDE("launchLat", (d) -> { return true; }),
	
	/**
	 * The longitude of the launch site
	 */
	LAUNCH_LONGITUDE("launchLong", (d) -> { return true; }),
	
	/**
	 * The distance above sea level in meters
	 */
	LAUNCH_ALTITUDE("launchAlt", (d) -> { return true; }),
	
	/**
	 * Honestly haven't a clue what this does
	 */
	MAXIMUM_ANGLE("maxAngle", (d) -> { return true; }),
	
	/**
	 * The speed of wind at the launch altitude
	 * TODO: Units?
	 */
	WIND_SPEED("windSpeed", (d) -> { return true; }),
	
	/**
	 * The direction of the wind
	 * TODO: Unit?
	 */
	WIND_DIRECTION("windDir", (d) -> { return true; }),
	
	/**
	 * The temperature, in kelvin, at the launch site
	 */
	TEMPERATURE("launchTemp", (d) -> { return d > 0; }),
	
	/**
	 * The air pressure at the launch site
	 * TODO: Unit?
	 */
	AIR_PRESSURE("launchAirPressure", (d) -> { return true; }),
	
	/**
	 * The wind turbulence
	 * TODO: Unit?
	 */
	WIND_TURBULENCE("windTurbulence", (d) -> { return true; }),
	
	/**
	 * The number of simulations to run
	 */
	SIMULATIONS_TO_RUN("numSimulations", (d) -> { return d > 0.5; });
	
	/**
	 * The name of the field as per writing to CSV
	 */
	private final String name;
	
	/**
	 * A predicate that determines whether this value makes sense or not
	 */
	private final Predicate<Double> isValid;
	
	/**
	 * @param name
	 * @param isValid
	 */
	private MonteCarloConfigValue(String name, Predicate<Double> isValid) {
		this.name = name;
		this.isValid = isValid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the isValid
	 */
	public Predicate<Double> getIsValid() {
		return this.isValid;
	}
	
}
