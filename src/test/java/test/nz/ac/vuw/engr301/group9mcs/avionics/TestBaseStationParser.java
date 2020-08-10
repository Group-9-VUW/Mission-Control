package test.nz.ac.vuw.engr301.group9mcs.avionics;

import nz.ac.vuw.engr301.group9mcs.avionics.BaseStationParser;
import nz.ac.vuw.engr301.group9mcs.commons.RocketData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBaseStationParser {

    private BaseStationParser parser = new BaseStationParser();

    /**
     * Checks if the parsed data holds the correct values.
     */
    @Test
    public void checkCorrectness(){
        String testData = "192872,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,ARMED";
        RocketData parsedData = parser.parse(testData);

        assertEquals(192872, parsedData.getTimestamp());

        assertEquals(-41.335, parsedData.getLatitude());
        assertEquals(174.705, parsedData.getLongitude());

        assertEquals(4, parsedData.getGimbalX());
        assertEquals(3, parsedData.getGimbalY());

        assertEquals(3.60, parsedData.getAccelerationX());
        assertEquals(-0.16, parsedData.getAccelerationY());
        assertEquals(8.11, parsedData.getAccelerationZ());

        assertEquals(-0.03, parsedData.getRotationX());
        assertEquals(-0.89, parsedData.getRotationY());
        assertEquals(0.12, parsedData.getRotationZ());

        assertEquals(26.23, parsedData.getHumidity());
        assertEquals(0, parsedData.getTemperature());
        assertEquals(6.53, parsedData.getAirPressure());

        assertEquals(RocketData.ROCKET_STATE.ARMED, parsedData.getState());

    }
}
