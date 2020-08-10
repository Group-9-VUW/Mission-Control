package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Holds one instance of rocket data.
 */
public class RocketData {

    /**
     * Possible values for the rocket state value returned from the base station.
     */
    public static enum ROCKET_STATE {
        ARMED,
        FLIGHT
    }

    /**
     * This is the current clock time on the rocket.
     */
    private final double timestamp;

    /**
     * Latitude of the rocket.
     */
    private final double latitude;

    /**
     * Longitude of the rocket.
     */
    private final double longitude;

    /**
     * The x position of the gimbal.
     */
    private final double gimbalX;

    /**
     * The y position of the gimbal;
     */
    private final double gimbalY;

    /**
     * The IMU (Inertial measurement unit) reading for the x axis acceleration
     * of the rocket.
     */
    private final double accelerationX;

    /**
     * The IMU (Inertial measurement unit) reading for the y axis acceleration
     * of the rocket.
     */
    private final double accelerationY;

    /**
     * The IMU (Inertial measurement unit) reading for the z axis acceleration
     * of the rocket.
     */
    private final double accelerationZ;

    /**
     * The IMU (Inertial measurement unit) reading for the z axis rotation
     * of the rocket.
     */
    private final double rotationX;

    /**
     * The IMU (Inertial measurement unit) reading for the z axis rotation
     * of the rocket.
     */
    private final double rotationY;

    /**
     * The IMU (Inertial measurement unit) reading for the z axis rotation
     * of the rocket.
     */
    private final double rotationZ;

    /**
     * The barometer reading for the humidity outside the rocket.
     */
    private final double humidity;

    /**
     * The barometer reading for the humidity outside the rocket.
     */
    private final double temperature;

    /**
     * The barometer reading for the humidity outside the rocket.
     */
    private final double airPressure;

    /**
     * The state of the rocket.
     */
    private final ROCKET_STATE state;

    /**
     * Initialises all fields.
     * @param timestamp current clock on the rocket.
     * @param latitude latitude of the rocket.
     * @param longitude longitude of the rocket.
     * @param gimbalX x position of the gimbal.
     * @param gimbalY y position of the gimbal.
     * @param accelerationX acceleration in the x axis direction.
     * @param accelerationY acceleration in the y axis direction.
     * @param accelerationZ acceleration in the z axis direction.
     * @param rotationX rotation about the x axis.
     * @param rotationY rotation about the y axis.
     * @param rotationZ rotation about the z axis.
     * @param humidity humidty outside the rocket.
     * @param temperature temperature outside the rocket.
     * @param airPressure air pressure outside the rocket.
     * @param state state of the rocket.
     */
    public RocketData(double timestamp, double latitude, double longitude, double gimbalX, double gimbalY,
                      double accelerationX, double accelerationY, double accelerationZ,
                      double rotationX, double rotationY, double rotationZ,
                      double humidity, double temperature, double airPressure, ROCKET_STATE state){
        this.timestamp = timestamp;
        this.latitude = latitude;

        this.longitude = longitude;
        this.gimbalX = gimbalX;
        this.gimbalY = gimbalY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.accelerationZ = accelerationZ;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.humidity = humidity;
        this.temperature = temperature;
        this.airPressure = airPressure;
        this.state = state;
    }

    /**
     * Get the timestamp.
     * @return the timestamp of the clock on the rocket.
     */
    public double getTimestamp() {
        return timestamp;
    }

    /**
     * Get the latitude.
     * @return the latitude of the rocket.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude.
     * @return the longtitude of the rocket.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get the gimbal x.
     * @return the x value of the gimbal.
     */
    public double getGimbalX() {
        return gimbalX;
    }

    /**
     * Get the gimbal y.
     * @return the y value of the gimbal.
     */
    public double getGimbalY() {
        return gimbalY;
    }

    /**
     * Get the acceleration for the x axis.
     * @return the acceleration for the x axis.
     */
    public double getAccelerationX() {
        return accelerationX;
    }

    /**
     * Get the acceleration for the y axis.
     * @return the acceleration for the y axis.
     */
    public double getAccelerationY() {
        return accelerationY;
    }

    /**
     * Get the acceleration for the z axis.
     * @return the acceleration for the z axis.
     */
    public double getAccelerationZ() {
        return accelerationZ;
    }

    /**
     * Get the rotation about the x axis.
     * @return the rotation about the x axis.
     */
    public double getRotationX() {
        return rotationX;
    }

    /**
     * Get the rotation about the y axis.
     * @return the rotation about the y axis.
     */
    public double getRotationY() {
        return rotationY;
    }

    /**
     * Get the rotation about the z axis.
     * @return the rotation about the z axis.
     */
    public double getRotationZ() {
        return rotationZ;
    }

    /**
     * Get the humidity outside the rocket.
     * @return the humidity outside the rocket.
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Get the temperature outside the rocket.
     * @return the temperature outside the rocket.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Get the air pressure outside the rocket.
     * @return the air pressure outside the rocket.
     */
    public double getAirPressure() {
        return airPressure;
    }

    /**
     * Get the state of the rocket.
     * @return the state of the rocket.
     */
    public ROCKET_STATE getState() {
        return state;
    }

    @Override
    public String toString() {
        return "RocketData{" +
                "timestamp=" + timestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", gimbalX=" + gimbalX +
                ", gimbalY=" + gimbalY +
                ", accelerationX=" + accelerationX +
                ", accelerationY=" + accelerationY +
                ", accelerationZ=" + accelerationZ +
                ", rotationX=" + rotationX +
                ", rotationY=" + rotationY +
                ", rotationZ=" + rotationZ +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", airPressure=" + airPressure +
                ", state=" + state +
                '}';
    }
}
