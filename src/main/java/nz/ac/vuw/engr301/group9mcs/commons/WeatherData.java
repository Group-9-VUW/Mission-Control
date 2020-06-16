package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of Weather Data. 
 */
public class WeatherData {
	private final double temperature; 
	private final double windSpeed; 
	private final double pressure; 
	private final double precipitation; 
	
	public WeatherData(double temperature, double windSpeed, double pressure, double precipitation) {
		this.temperature = temperature; 
		this.windSpeed = windSpeed;
		this.pressure = pressure;
		this.precipitation = precipitation;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @return the windSpeed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @return the pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * @return the precipitation
	 */
	public double getPrecipitation() {
		return precipitation;
	}
}
