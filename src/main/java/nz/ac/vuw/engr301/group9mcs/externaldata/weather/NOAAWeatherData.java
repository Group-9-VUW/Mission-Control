package nz.ac.vuw.engr301.group9mcs.externaldata.weather;

/**
 * Holds one instance of NOAA weather data (one reading at a corresponding altitude). 
 * @author Sai
 */
public class NOAAWeatherData implements Comparable<NOAAWeatherData> {
    /**
     * The altitude of the reading.
     */
    private double altitude;

    /**
     * The wind speed of the reading in M/s.
     */
    private double windSpeed;
    
    /**
     * The wind direction of the reading in degrees.
     */
    private double windDirection;

    /**
     * The temperature of the reading in Kelvin.
     */
    private double temperature;

    /**
     * The pressure of the reading in Bars (1 Bar = 100000 Pascals).
     */
    private double pressure;


    /**
     * Creates the NOAAWeatherData object with the supplied parameters from one particular reading at a specified altitude. 
     * @param altitude of the reading. 
     * @param windSpeed at the corresponding altitude.
     * @param windDirection at the corresponding altitude.
     * @param temperature at the corresponding altitude.
     * @param pressure at the corresponding altitude.
     */
    public NOAAWeatherData(double altitude, double windSpeed, double windDirection, double temperature, double pressure) {
        this.altitude = altitude;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    /**
     * Get the altitude of the reading.
     * @return the altitude of the reading.
     */
    public double getAltitude() {
        return this.altitude;
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
        return "altitude=" + this.altitude +
                ", windSpeed=" + this.windSpeed +
                ", windDirection=" + this.windDirection +
                ", temperature=" + this.temperature +
                ", pressure=" + this.pressure;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     */
    @Override
    public int compareTo(NOAAWeatherData o) {
        if (this.altitude < o.getAltitude()) {
            return -1;
        } else if (this.altitude > o.getAltitude()) {
            return 1;
        } else {
            return 0;
        }
    }
}
