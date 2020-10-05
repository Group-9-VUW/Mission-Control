package test.nz.ac.vuw.engr301.group9mcs.externaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAAWeatherData;

/**
 * Tests the implementation of NOAAWeatherData. 
 * @author Sai
 */
public class TestNOAAWeatherData {
	
	/**
	 * Checks if the toString method for NOAAWeatherData outputs the correct info.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkToString() {
		NOAAWeatherData data = new NOAAWeatherData(200, 3.4823615550994873, 85.77337646484375, 287.0677185058594, 100000.0);
		assertEquals("altitude=200.0" +
                ", windSpeed=3.4823615550994873" +
                ", windDirection=85.77337646484375" +
                ", temperature=287.0677185058594" +
                ", pressure=100000.0" ,
                data.toString());
	}

	/**
	 * Checks the correctness of the data stored in the NOAAWeatherData object.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void checkCorrectness() {
		NOAAWeatherData data = new NOAAWeatherData(200, 3.4823615550994873, 85.77337646484375, 287.0677185058594, 100000.0);
		assertEquals(200, data.getAltitude());
		assertEquals(3.4823615550994873, data.getWindSpeed());
		assertEquals(85.77337646484375, data.getWindDirection());
		assertEquals(287.0677185058594, data.getTemperature());
		assertEquals( 100000.0, data.getPressure());

	}
}
