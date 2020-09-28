package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.OWWeatherData;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.OWMGetter;

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
	
	/**
	 * The default NOAAGetter.
	 */
    private OWMGetter getter = new OWMGetter("ead647e24776f26ed6f63af5f1bbf68c");

    /**
     * Determines whether the NOAAGetter can connect to NOAA.
     * @return whether NOAA can be reached or not
     */
    private static boolean canConnect() {
        return OWMGetter.isAvailable();
    }

    /**
     * Tests if the appID is correctly set within the class.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testAppID() {
        OWMGetter testGetter = new OWMGetter("");
        testGetter.setAppId("ead647e24776f26ed6f63af5f1bbf68");
        assertEquals("ead647e24776f26ed6f63af5f1bbf68", testGetter.getAppId());
    }
    
    
    /**
     * Tests if the OWWeatherData objects created are correct.
     */
    @SuppressWarnings("static-method")
	@Test
    public void testCorrectness() {
    	try {
			OWWeatherData data = new OWWeatherData(20, 50, 80,
					112, 20, 8, 0);
			
			assertEquals(20, data.getTemperature());
			assertEquals(50, data.getWindSpeed());
			assertEquals(80, data.getWindDegrees());
			assertEquals(112,data.getPressure());
			assertEquals(20, data.getPrecipitation());
			assertEquals(8, data.getHumidity());
			assertEquals(0, data.getCloudiness());
			assertEquals("OWWeatherData [temperature=" + 20.0 + ", windSpeed=" + 50.0 + ", windDegrees=" + 80.0
				+ ", pressure=" + 112.0 + ", precipitation=" + 20.0 + ", humidity=" + 8.0
				+ ", cloudiness=" + 0.0 + "]",
				data.toString());
		} catch (@SuppressWarnings("unused") InvalidParameterException e) {
			fail("Exception should not be thrown, either OpenWeatherMap "
					+ "is down or there is a bug in the program.");
		}
    }
    
    /**
     * Tests that getWeatherData does not accept latitude values that are too low.
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
        } catch (@SuppressWarnings("unused") IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    /**
     * Tests that getWeatherData does not accept latitude values that are too high.
     */
    @Test
    public void testInvalidLatitude2() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Latitude must be within the range [-90, 90]", e.getMessage());
        } catch (@SuppressWarnings("unused") IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }

    /**
     * Tests that getWeatherData does not accept longitude values that are too low.
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
        } catch (@SuppressWarnings("unused") IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    /**
     * Tests that getWeatherData does not accept longitude values that are too high.
     */
    @Test
    public void testInvalidLongitude2() {
        assertTrue(canConnect());
        try {
            this.getter.getWeatherData(20, 181);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        	// Correct exception as the supplied parameters are invalid. 
        	assertEquals("Longitude must be within the range [-180, 180]", e.getMessage());
        } catch (@SuppressWarnings("unused") IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    /**
     * Tests that getWeatherData does not accept latitude and longitude values that are too low.
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
        } catch (@SuppressWarnings("unused") IOException e) {
        	// This exception should not be thrown, unless the machine running the tests has no 
        	// internet connection, or the weather API is down. 
        	assumeTrue(false); //skips the test
		}
    }
    
    /**
     * Tests that getWeatherData does not accept latitude and longitude values that are too high.
     */
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
        } catch (@SuppressWarnings("unused") IOException e) {
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
