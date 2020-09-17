package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of NOAA weather data (one reading at a corresponding altitude). 
 * @author Sai
 */
public class NOAAWeatherData {

    /**
     * The wind speed of the reading.
     */
    private double windSpeed;
    
    /**
     * The wind direction of the reading.
     */
    private double windDirection;

    /**
     * The temperature of the reading. 
     */
    private double temperature;

    /**
     * The pressure of the reading. 
     */
    private double pressure;


    /**
     * Creates the NOAAWeatherData object with the supplied parameters from one particular reading at a specified altitude. 
     * @param windSpeed at the corresponding altitude.
     * @param windDirection at the corresponding altitude.
     * @param temperature at the corresponding altitude.
     * @param pressure at the corresponding altitude.
     */
    public NOAAWeatherData(double windSpeed, double windDirection, double temperature, double pressure) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.pressure = pressure;
    }


    /**
     * Get the wind speed reading at this altitude. 
     * @return the wind speed from the reading.
     */
    public double getWindSpeed() {
        return this.windSpeed;
    }

    /**
     * Get the wind direction reading at this altitude. 
     * @return the wind direction from the reading.
     */
    public double getWindDirection() {
        return this.windDirection;
    }

    /**
     * Get the temperature reading at this altitude. 
     * @return the temperature from the reading.
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * Get the pressure reading at this altitude. 
     * @return the pressure from the reading. 
     */
    public double getPressure() {
        return this.pressure;
    }

    @Override
    public String toString() {
        return "NOAAWeatherData{" +
                "windSpeed=" + this.windSpeed +
                ", windDirection=" + this.windDirection +
                ", temperature=" + this.temperature +
                ", pressure=" + this.pressure +
                '}';
    }
}
