/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.vuw.engr301.group9mcs.commons.Null;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAWeatherData;

/**
 * @author Claire
 */
public class MonteCarloConfigBuilder {
	
	/**
	 * A set of all values that are absolutely required to run the simulation
	 */
	private static final EnumSet<MonteCarloConfigValue> REQUIRED = Null.nonNull(EnumSet.copyOf(Arrays.<MonteCarloConfigValue>asList(MonteCarloConfigValue.LAUNCH_ROD_ANGLE, 
			MonteCarloConfigValue.LAUNCH_ROD_DIRECTION, MonteCarloConfigValue.LAUNCH_ROD_DIRECTION, MonteCarloConfigValue.LAUNCH_ALTITUDE,
			MonteCarloConfigValue.LAUNCH_LONGITUDE, MonteCarloConfigValue.LAUNCH_LATITUDE,
			MonteCarloConfigValue.WIND_SPEED, MonteCarloConfigValue.WIND_DIRECTION, MonteCarloConfigValue.WIND_TURBULENCE,
			MonteCarloConfigValue.TEMPERATURE, MonteCarloConfigValue.AIR_PRESSURE, 
			MonteCarloConfigValue.MAXIMUM_ANGLE, MonteCarloConfigValue.SIMULATIONS_TO_RUN)));	
	
	/**
	 * The values that this object represents
	 */
	private final Map<MonteCarloConfigValue, Double> values = new HashMap<>();
	
	/**
	 * Partially initializes the builder with some default values
	 */
	public MonteCarloConfigBuilder()
	{
		this.addValue(MonteCarloConfigValue.LAUNCH_ROD_DIRECTION, 0).addValue(MonteCarloConfigValue.LAUNCH_ROD_ANGLE, 0)
			.addValue(MonteCarloConfigValue.MAXIMUM_ANGLE, 0.036);
	}
	
	/**
	 * @param target The number of simulations to run
	 * @return This object, modified
	 */
	public MonteCarloConfigBuilder addSimulationTarget(int target)
	{
		return this.addValue(MonteCarloConfigValue.SIMULATIONS_TO_RUN, target);
	}
	
	/**
	 * @param data The weather data at the launch site at the time of launch
	 * @return This object, modified
	 */
	public MonteCarloConfigBuilder addWeather(List<NOAAWeatherData> data)
	{
		return this.addValue(MonteCarloConfigValue.LAUNCH_ALTITUDE, data.get(0).getAltitude())
			       .addValue(MonteCarloConfigValue.WIND_SPEED, data.get(0).getWindSpeed())
			       .addValue(MonteCarloConfigValue.WIND_DIRECTION, data.get(0).getWindDirection())
			       .addValue(MonteCarloConfigValue.TEMPERATURE, data.get(0).getTemperature())
			       .addValue(MonteCarloConfigValue.AIR_PRESSURE, data.get(0).getPressure())
			       .addValue(MonteCarloConfigValue.WIND_TURBULENCE, 0.1);
	}
	
	/**
	 * @param lat The latitude of the launch site
	 * @param lon The longitude of the launch site
	 * @return This object, modified
	 */
	public MonteCarloConfigBuilder addPosition(double lat, double lon)
	{
		return this.addValue(MonteCarloConfigValue.LAUNCH_LATITUDE, lat)
				   .addValue(MonteCarloConfigValue.LAUNCH_LONGITUDE, lon);
	}

	/**
	 * @param angle The angle of the launch rod
	 * @param direction The direction of the launch rod
	 * @param length The length of the launch rod
	 * @return This object, modified
	 */
	public MonteCarloConfigBuilder addLaunchRodData(double angle, double direction, double length)
	{
		return this.addValue(MonteCarloConfigValue.LAUNCH_ROD_ANGLE, angle)
				   .addValue(MonteCarloConfigValue.LAUNCH_ROD_DIRECTION, direction)
				   .addValue(MonteCarloConfigValue.LAUNCH_ROD_HEIGHT, length);
	}
	
	/**
	 * @param type The type/key of the value being added
	 * @param value The value
	 * @return This object, updated
	 */
	public MonteCarloConfigBuilder addValue(MonteCarloConfigValue type, double value)
	{
		this.values.put(type, value);
		return this;
	}
	
	/**
	 * @return Whether or not this configuration contains all the required
	 */
	public boolean isComplete()
	{
		EnumSet<MonteCarloConfigValue> set = EnumSet.copyOf(this.values.keySet());
		return set.containsAll(REQUIRED);
	}
	
	/**
	 * @return Produces a final version of this config builder
	 */
	public MonteCarloConfig build()
	{
		return new MonteCarloConfig(new HashMap<>(this.values));
	}

}
