package test.nz.ac.vuw.engr301.group9mcs.avionics;

import nz.ac.vuw.engr301.group9mcs.avionics.BaseStationParser;
import nz.ac.vuw.engr301.group9mcs.commons.RocketData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the BaseStationParser. Particularly the correctness of the data and whether the parser throws the 
 * correct exceptions. 
 * 
 * @author pandasai
 */
public class TestBaseStationParser {

    private BaseStationParser parser = new BaseStationParser();

    /**
     * Checks if the parsed data holds the correct values.
     */
    @Test
    public void checkCorrectness(){
        String testData = "192872,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,ARMED";
        RocketData parsedData = this.parser.parse(testData);

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
    
    /**
     * Checks if the parser throws an error when passed an invalid type for a double. 
     */
    @Test
    public void checkInvalidDouble() {
    	try {
            String testData = "invalid_double,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,ARMED";
            this.parser.parse(testData);
            fail("NumberFormatException should be thrown.");
    	} catch(NumberFormatException e) {
    		assertEquals("invalid_double is not a double.", e.getMessage());
    	}
    }
    
    /**
     * Checks if the parser throws an error when passed an invalid rocket state. 
     */
    @Test
    public void checkInvalidState() {
    	try {
    		String testData = "192872,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,invalid_state";
    		this.parser.parse(testData);
    		fail("IllegalArguementException should be thrown");
    	} catch(IllegalArgumentException e) {
    		assertEquals("invalid_state is not a valid rocket state.", e.getMessage());
    	}
    }
    
}
