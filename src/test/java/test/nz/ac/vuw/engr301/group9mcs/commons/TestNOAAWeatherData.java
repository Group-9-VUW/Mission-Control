package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.NOAAWeatherData;

/**
 * Tests the implementation of NOAAWeatherData. 
 * @author Sai
 */
public class TestNOAAWeatherData {
	
	/**
	 * Checks if the toString method for NOAAWeatherData outputs the correct info. 
	 */
	@Test
	public void checkToString() {
		NOAAWeatherData data = new NOAAWeatherData(3.4823615550994873, 85.77337646484375, 287.0677185058594, 100000.0);
		assertEquals("NOAAWeatherData{" +
                "windSpeed=3.4823615550994873" +
                ", windDirection=85.77337646484375" + 
                ", temperature=287.0677185058594" +
                ", pressure=100000.0" +
                '}', 
                data.toString());
	}
}
