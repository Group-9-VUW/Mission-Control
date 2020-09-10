package nz.ac.vuw.engr301.group9mcs.commons;

public class NOAAWeatherData {

    private double altitude;

    private double windSpeed;

    private double windDirection;

    private double temperature;

    private double pressure;


    public NOAAWeatherData(double altitude, double windSpeed, double windDirection, double temperature, double pressure){
        this.altitude = altitude;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }
}
