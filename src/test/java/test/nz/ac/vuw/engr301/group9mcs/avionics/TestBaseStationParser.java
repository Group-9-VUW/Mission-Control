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
 * @author Sai Panda
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestBaseStationParser {

	/**
	 * The parser to be used for the test(s).
	 */
	private BaseStationParser parser = new BaseStationParser();

	/**
	 * Checks if the parsed data holds the correct values.
	 */
	@Test
	public void testCorrectness() {
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

		assertEquals("RocketData{" +
				"timestamp=" + 192872.0 +
				", latitude=" + -41.335 +
				", longitude=" + 174.705 +
				", gimbalX=" + 4.0 +
				", gimbalY=" + 3.0 +
				", accelerationX=" + 3.60 +
				", accelerationY=" + -0.16 +
				", accelerationZ=" + 8.11 +
				", rotationX=" + -0.03 +
				", rotationY=" + -0.89 +
				", rotationZ=" + 0.12 +
				", humidity=" + 26.23 +
				", temperature=" + 0.0 +
				", airPressure=" + 6.53 +
				", state=" + "ARMED" +
				'}', parsedData.toString());
	}

	/**
	 * Checks if the parser throws an error when passed an invalid type for a double.
	 */
	@Test
	public void testInvalidDouble() {
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
	public void testInvalidState() {
		try {
			String testData = "192872,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,invalid_state";
			this.parser.parse(testData);
			fail("IllegalArguementException should be thrown");
		} catch(IllegalArgumentException e) {
			assertEquals("invalid_state is not a valid rocket state.", e.getMessage());
		}
	}

}
