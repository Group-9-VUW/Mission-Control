package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import nz.ac.vuw.engr301.group9mcs.commons.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.externaldata.NOAAGetter;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

import static junit.framework.TestCase.fail;

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
        assert testGetter.getAppId().equals("ead647e24776f26ed6f63af5f1bbf68");
    }

    /**
     * Tests that getWeatherData does not accept invalid latitude values
     */
    @Test
    public void testInvalidLatitude() {
        assert (canConnect());
        try {
            getter.getWeatherData(-91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        }
    }

    /**
     * Tests that getWeatherData does not accept invalid longitude values
     */
    @Test
    public void testInvalidLongitude(){
        assert(canConnect());
        try{
            getter.getWeatherData(20, -181);
            fail("InvalidParameterException should be thrown");
        } catch(InvalidParameterException e){
        }
    }

    /**
     * Tests that getWeatherData returns weather data with valid latitude and longitude
     */
    @Test
    public void testCorrectResponse(){
        assert(canConnect());
        assert getter.getWeatherData(41, 175) != null;
    }

    /**
     * Tests that getForecast returns weather data with valid latitude and longitude
     */
    @Test
    public void testCorrectForecastResponse(){
        assert(canConnect());
        assert getter.getForecast(41, 175) != null;
    }

}
