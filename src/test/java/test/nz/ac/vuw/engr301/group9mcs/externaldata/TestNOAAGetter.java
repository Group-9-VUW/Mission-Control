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

    @Test
    public void testInvalidLatitude() {
        assert (canConnect());
        try {
            getter.getWeatherData(-91, 20);
            fail("InvalidParameterException should be thrown");
        } catch (InvalidParameterException e) {
        }
    }

    @Test
    public void testInvalidLongitude(){
        assert(canConnect());
        try{
            getter.getWeatherData(20, -181);
            fail("InvalidParameterException should be thrown");
        } catch(InvalidParameterException e){
        }
    }

    @Test
    public void testCorrectResponse(){
        assert(canConnect());
        assert getter.getWeatherData(41, 175) != null;
    }

    @Test
    public void testCorrectForecastResponse(){
        assert(canConnect());
        assert getter.getForecast(41, 175) != null;
    }
}
