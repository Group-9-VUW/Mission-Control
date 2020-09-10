package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This class tests the implementation of the NOAAGetter class. More specifically, it tests the responses from the 
 * weather API and also tests if the weather methods reject invalid inputs. 
 * @author pandasai
 * @author Joshua Hindley
 * @editor Joshua Hindley
 */
public class TestNOAAGetter {
    private NOAAGetter getter = new NOAAGetter("ead647e24776f26ed6f63af5f1bbf68c");

    private static boolean canConnect() {
        return NOAAGetter.isAvailable();
    }

    /**
     * Tests if the appID is correctly set within the class.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testAppID() {
        NOAAGetter testGetter = new NOAAGetter("");
        testGetter.setAppId("ead647e24776f26ed6f63af5f1bbf68");
        assertEquals("ead647e24776f26ed6f63af5f1bbf68", testGetter.getAppId());
    }
    
    /**
     * These tests are commented out for now as we need to have a centralised logger for testing. The tests used to
     * catch an exception being thrown, but now they need to check if the logger is logging properly. This will 
     * be implemented at a later date. 
     */
    
    /**
     * Tests that getWeatherData does not accept invalid latitude values.
     */
    @Test
    public void testInvalidLatitude1() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(-91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Latitude must be within the range [-90, 90]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    @Test
    public void testInvalidLatitude2() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Latitude must be within the range [-90, 90]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }

    /**
     * Tests that getWeatherData does not accept invalid longitude values.
     */
    @Test
    public void testInvalidLongitude1() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(20, -181);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Longitude must be within the range [-180, 180]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    @Test
    public void testInvalidLongitude2() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(20, 181);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Longitude must be within the range [-180, 180]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    /**
     * Tests that getWeatherData does not accept invalid longitude and longitude values.
     */
    @Test
    public void testInvalidLatAndLong1() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(-91, -181);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Latitude must be within the range [-90, 90] and "
        			+ "Longitude must be within the range [-180, 180]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    @Test
    public void testInvalidLatAndLong2() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(91, 181);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Latitude must be within the range [-90, 90] and "
        			+ "Longitude must be within the range [-180, 180]", e.getMessage());
        } catch (IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }

    /**
     * Tests that getWeatherData returns weather data with valid latitude and longitude.
     */
    @Test
    public void testCorrectResponse() {
        assertTrue(canConnect());
        try {
			assertNotNull(this.getter.getWeatherData(41, 175));
		} catch (InvalidParameterException e) {
			fail("InvalidParameterException should not be thrown: " + e.getMessage());
		} catch (IOException e) {
			fail("IOException should not be thrown: " + e.getMessage());
		}
    }

    /**
     * Tests that getForecast returns weather data with valid latitude and longitude.
     */
    @Test
    public void testCorrectForecastResponse() {
        assertTrue(canConnect());
        try {
			assertNotNull(this.getter.getForecast(41, 175));
		} catch (InvalidParameterException e) {
			fail("InvalidParameterException should not be thrown: " + e.getMessage());
		} catch (IOException e) {
			fail("IOException should not be thrown: " + e.getMessage());
		}
    }
}
