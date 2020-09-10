package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of NOAA weather data (one reading at a corresponding altitude). 
 * @author Sai
 */
public class NOAAWeatherData {

    private double altitude;

    private double windSpeed;

    private double windDirection;

    private double temperature;

    private double pressure;


    /**
     * @param altitude the height above sea level of the reading. 
     * @param windSpeed at the corresponding altitude.
     * @param windDirection at the corresponding altitude.
     * @param temperature at the corresponding altitude.
     * @param pressure at the corresponding altitude.
     */
    public NOAAWeatherData(double altitude, double windSpeed, double windDirection, double temperature, double pressure){
        this.altitude = altitude;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    /**
     * @return the altidude from the reading.
     */
    public double getAltitude() {
        return this.altitude;
    }

    /**
     * @return the wind speed from the reading.
     */
    public double getWindSpeed() {
        return this.windSpeed;
    }

    /**
     * @return the wind direction from the reading.
     */
    public double getWindDirection() {
        return this.windDirection;
    }

    /**
     * @return the temperature from the reading.
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * @return the pressure from the reading. 
     */
    public double getPressure() {
        return this.pressure;
    }
}
