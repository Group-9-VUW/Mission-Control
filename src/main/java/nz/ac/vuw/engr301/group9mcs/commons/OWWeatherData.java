package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of Weather Data. 
 */
public class OWWeatherData {
	/**
	 * The temperature reading in celsius. 
	 */
	private final double temperature; 
	/**
	 * The wind speed reading in km/h.
	 */
	private final double windSpeed; 
	
	/**
	 * The wind degrees (meteorological)
	 */
	private final double windDegrees; 
	
	/**
	 * The atmospheric pressure in hPA.
	 */
	private final double pressure; 
	/**
	 * The precipitation in mm.
	 */
	private final double precipitation; 
	
	/**
	 * Humidity in %.
	 */
	private final double humidity;
	
	/**
	 * Cloudiness in %. 
	 */
	private final double cloudiness;
	
	
	/**
	 * @param temperature - temperature reading in celsius. 
	 * @param windSpeed - wind speed reading in km/h.
	 * @param windDegrees - wind degrees in meteorological units. 
	 * @param pressure - atmospheric pressure in hPA.
	 * @param precipitation - precipitation in mm.
	 * @param humidity - the amount of humidity in %. 
	 * @param cloudiness - the amount of cloudiness in %. 
	 */
	public OWWeatherData(double temperature, double windSpeed, double windDegrees, double pressure, double precipitation,
						 double humidity, double cloudiness) {
		this.temperature = temperature; 
		this.windSpeed = windSpeed;
		this.windDegrees = windDegrees; 
		this.pressure = pressure;
		this.precipitation = precipitation;
		this.humidity = humidity; 
		this.cloudiness = cloudiness;
	}

	/**
	 * @return the temperature.
	 */
	public double getTemperature() {
		return this.temperature;
	}

	/**
	 * @return the windSpeed.
	 */
	public double getWindSpeed() {
		return this.windSpeed;
	}

	/**
	 * @return the pressure.
	 */
	public double getPressure() {
		return this.pressure;
	}

	/**
	 * @return the precipitation.
	 */
	public double getPrecipitation() {
		return this.precipitation;
	}
	
	/**
	 * @return the humidity.
	 */
	public double getHumidity() {
		return this.humidity;
	}
	
	/**
	 * @return the cloudiness. 
	 */
	public double getCloudiness() {
		return this.cloudiness;
	}
	
	@Override
	public String toString() {
		return "Temperature: " + this.temperature + "°C \n" + "Wind Speed: " + this.windSpeed + "km/h \n" 
		+ "Wind Degrees: " + this.windDegrees + "° \n" + "Atmospheric Pressure (at sea level): " 
		+ this.pressure + "hPa \n" + "Precipitation (last hour): " + this.precipitation + "mm \n"
		+ "Humidity: " + this.humidity + "% \n" + "Cloudiness: " + this.cloudiness + "%";			
	}
}
