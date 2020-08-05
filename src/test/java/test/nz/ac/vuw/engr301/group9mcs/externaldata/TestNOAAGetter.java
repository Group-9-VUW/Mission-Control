package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class tests the implementation of the NOAAGetter class. More specifically, it tests the responses from the 
 * weather API and also tests if the weather methods reject invalid inputs. 
 * @author pandasai
 */
public class TestNOAAGetter {
    private NOAAGetter getter = new NOAAGetter("ead647e24776f26ed6f63af5f1bbf68c");

    private boolean canConnect(){
        return NOAAGetter.isAvailable();
    }

    /**
     * Tests if the appID is correctly set within the class
     */
    @Test
    public void testAppID(){
        NOAAGetter testGetter = new NOAAGetter("");
        testGetter.setAppId("ead647e24776f26ed6f63af5f1bbf68");
        assertEquals("ead647e24776f26ed6f63af5f1bbf68", testGetter.getAppId());
    }

    /**
     * Tests that getWeatherData does not accept invalid latitude values
     */
    @Test
    public void testInvalidLatitude() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(-91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        }
    }

    /**
     * Tests that getWeatherData does not accept invalid longitude values
     */
    @Test
    public void testInvalidLongitude(){
        assertTrue(canConnect());
        try{
            this.getter.getWeatherData(20, -181);
            fail("InvalidParameterException should be thrown");
        } catch(InvalidParameterException e){
        }
    }

    /**
     * Tests that getWeatherData returns weather data with valid latitude and longitude
     */
    @Test
    public void testCorrectResponse(){
        assertTrue(canConnect());
        assertNotNull(this.getter.getWeatherData(41, 175));
    }

    /**
     * Tests that getForecast returns weather data with valid latitude and longitude
     */
    @Test
    public void testCorrectForecastResponse(){
        assertTrue(canConnect());
        assertNotNull(this.getter.getForecast(41, 175));
    }

}
