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
    public void testInvalidLat(){
        assert(canConnect());
        try{
            getter.getWeatherData(-91, 20);
            fail("Exception should be thrown");
        } catch(InvalidParameterException e){

        }

    }
}
