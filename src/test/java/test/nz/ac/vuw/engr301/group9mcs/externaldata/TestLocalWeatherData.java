package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.LocalWeatherData;

/**
 * Tests the LocalWeatherData class.  
 * @author saisa
 */
public class TestLocalWeatherData {
	
	/**
	 * Tests the correctness of a LocalWeatherData object. 
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkCorrectness() {
		LocalWeatherData data = new LocalWeatherData(80, 85, 112, 20);
		
		assertEquals(80, data.getWindSpeed());
		assertEquals(85, data.getWindDirection());
		assertEquals(112, data.getPressure());
		assertEquals(20, data.getTemp());
		
		assertEquals("LocalWeatherData [windSpeed=" + 80.0 + ", windDirection=" 
		+ 85.0 + ", pressure=" + 112.0 + ", temp=" + 20.0 + "]", data.toString());
	}
}
