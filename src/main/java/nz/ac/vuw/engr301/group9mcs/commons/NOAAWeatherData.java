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
     * @param windSpeed at the corresponding altitude.
     * @param windDirection at the corresponding altitude.
     * @param temperature at the corresponding altitude.
     * @param pressure at the corresponding altitude.
     */
    public NOAAWeatherData(double windSpeed, double windDirection, double temperature, double pressure){
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.pressure = pressure;
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

    @Override
    public String toString() {
        return "NOAAWeatherData{" +
                "windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                '}';
    }
}
