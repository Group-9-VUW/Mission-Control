package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of Weather Data. 
 */
public class WeatherData {
	/**
	 * The temperature reading in celsius. 
	 */
	private final double temperature; 
	/**
	 * The wind speed reading in km/h
	 */
	private final double windSpeed; 
	/**
	 * The atmospheric pressure in hPA
	 */
	private final double pressure; 
	/**
	 * The precipitation in mm
	 */
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
	
	@Override
	public String toString() {
		return "Temperature: " + temperature + "°C \n" + "Wind Speed: " + windSpeed + "km/h \n" 
		+ "Atmospheric Pressure (at sea level): " + pressure + "hPa \n" + "Precipitation (last hour): " + precipitation + "mm";			
	}
}
