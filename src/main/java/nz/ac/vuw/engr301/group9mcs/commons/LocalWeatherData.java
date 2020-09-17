package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * A data class for local weather data
 * 
 * @author Claire
 */
public class LocalWeatherData {
	
	/**
	 * Wind speed in ms-1
	 */
	private final double windSpeed;
	
	/**
	 * Wind direction
	 */
	private final double windDirection;
	
	/**
	 * Barometric pressure
	 */
	private final double pressure;
	
	/**
	 * Temperature
	 */
	private final double temp;

	/**
	 * @param windSpeed
	 * @param windDirection
	 * @param pressure
	 * @param temp
	 */
	public LocalWeatherData(double windSpeed, double windDirection, double pressure, double temp) {
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.pressure = pressure;
		this.temp = temp;
	}

	/**
	 * @return the windSpeed
	 */
	public double getWindSpeed() {
		return this.windSpeed;
	}

	/**
	 * @return the windDirection
	 */
	public double getWindDirection() {
		return this.windDirection;
	}

	/**
	 * @return the pressure
	 */
	public double getPressure() {
		return this.pressure;
	}

	/**
	 * @return the temp
	 */
	public double getTemp() {
		return this.temp;
	}

}
