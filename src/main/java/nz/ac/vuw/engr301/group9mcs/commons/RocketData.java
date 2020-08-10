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
}
