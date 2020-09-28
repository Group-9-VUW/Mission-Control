/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.util.function.Function;
import java.util.function.Predicate;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;

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
	LAUNCH_ROD_ANGLE("launchRodAngle", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The length of the launch rod, in meters
	 */
	LAUNCH_ROD_HEIGHT("launchRodLength", (d) -> { return d > 0; }, defaultToStr()),
	
	/**
	 * The direction of the launch rod
	 * TODO: Units
	 */
	LAUNCH_ROD_DIRECTION("launchRodDir", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The latitude of the launch site
	 */
	LAUNCH_LATITUDE("launchLat", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The longitude of the launch site
	 */
	LAUNCH_LONGITUDE("launchLong", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The distance above sea level in meters
	 */
	LAUNCH_ALTITUDE("launchAlt", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * Honestly haven't a clue what this does
	 */
	MAXIMUM_ANGLE("maxAngle", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The speed of wind at the launch altitude
	 * TODO: Units?
	 */
	WIND_SPEED("windSpeed", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The direction of the wind
	 * TODO: Unit?
	 */
	WIND_DIRECTION("windDir", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The temperature, in kelvin, at the launch site
	 */
	TEMPERATURE("launchTemp", (d) -> { return d > 0; }, defaultToStr()),
	
	/**
	 * The air pressure at the launch site
	 * TODO: Unit?
	 */
	AIR_PRESSURE("launchAirPressure", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The wind turbulence
	 * TODO: Unit?
	 */
	WIND_TURBULENCE("windTurbulence", (d) -> { return true; }, defaultToStr()),
	
	/**
	 * The number of simulations to run
	 */
	SIMULATIONS_TO_RUN("numSimulations", (d) -> { return d > 0.5; }, intToStr());
	
	/**
	 * The name of the field as per writing to CSV
	 */
	private final String name;
	
	/**
	 * A predicate that determines whether this value makes sense or not
	 */
	private final Predicate<Double> isValid;
	
	/**
	 * A function that turns this value to a string
	 */
	private final Function<Double, String> toStringFunc;
	
	/**
	 * @param name
	 * @param isValid
	 * @param toStringFunc 
	 */
	private MonteCarloConfigValue(String name, Predicate<Double> isValid, Function<Double, String> toStringFunc) {
		this.name = name;
		this.isValid = isValid;
		this.toStringFunc = toStringFunc;
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

	/**
	 * @return the toStringFunc
	 */
	public Function<Double, String> getToStringFunc() {
		return this.toStringFunc;
	}
	
	/**
	 * @return Returns the default double to string function
	 */
	private static Function<Double, String> defaultToStr()
	{
		return (d) -> { return Null.nonNull(Double.toString(d)); };
	}
	
	/**
	 * @return A function that converts an integer packed as a double to a string 
	 */
	private static Function<Double, String> intToStr()
	{
		return (d) -> { return Null.nonNull(Integer.toString((int) Math.round(d))); };
	}
	
}
