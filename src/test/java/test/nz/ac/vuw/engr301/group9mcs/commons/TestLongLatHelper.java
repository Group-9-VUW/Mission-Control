package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.map.LongLatHelper;

/**
 * Tests the LongLatHelper class and the methods within.
 *
 * @author Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestLongLatHelper {

	/**
	 * Tests the kilometersPerDegreeOfLatitude and kilometersPerDegreeOfLongitude methods.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKilometersPerDegree() {
		LongLatHelper helper = new LongLatHelper();
		assertNotNull(helper);
		//km per degree of latitude
		assertEquals(110.567, LongLatHelper.kilometersPerDegreeOfLatitude(0));
		//km per degree of longitude at equator
		assertEquals(111.3215, LongLatHelper.kilometeresPerDegreeOfLongitude(0));
		//km per degree of longitude at -40 degrees (approx over NZ)
		assertTrue(Math.abs(85.277216 - LongLatHelper.kilometeresPerDegreeOfLongitude(-40)) < 0.00001);
	}

	/**
	 * Tests the latitudeNKilometersNorth and latitudeNKilometersSouth methods.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKilometersNorthAndSouth() {
		//at the equator
		assertEquals(1, LongLatHelper.latitudeNKilometersNorth(0, 110.567));
		assertEquals(-1, LongLatHelper.latitudeNKilometersSouth(0, 110.567));
		//at -40 degrees (approx. over NZ)
		assertEquals(-39, LongLatHelper.latitudeNKilometersNorth(-40, 110.567));
		assertEquals(-41, LongLatHelper.latitudeNKilometersSouth(-40, 110.567));
		//two degree change
		assertEquals(-38, LongLatHelper.latitudeNKilometersNorth(-40, 221.134));
		assertEquals(-42, LongLatHelper.latitudeNKilometersSouth(-40, 221.134));
		//0.5 degree change
		assertEquals(-39.5, LongLatHelper.latitudeNKilometersNorth(-40, 55.2835));
		assertEquals(-40.5, LongLatHelper.latitudeNKilometersSouth(-40, 55.2835));
	}

	/**
	 * Tests the longitudeNKilometersEast and longitudeNKilometersWest methods.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKilometersEastAndWest() {
		//at 0,0
		assertTrue(Math.abs(0.99322 - LongLatHelper.longitudeNKilometersEast(0, 0, 110.567)) < 0.00001);
		assertTrue(Math.abs(-0.99322 - LongLatHelper.longitudeNKilometersWest(0, 0, 110.567)) < 0.00001);
		//elsewhere at the equator
		assertTrue(Math.abs(45.99322 - LongLatHelper.longitudeNKilometersEast(0, 45, 110.567)) < 0.00001);
		assertTrue(Math.abs(44.00678 - LongLatHelper.longitudeNKilometersWest(0, 45, 110.567)) < 0.00001);
		assertTrue(Math.abs(-44.00678 - LongLatHelper.longitudeNKilometersEast(0, -45, 110.567)) < 0.00001);
		assertTrue(Math.abs(-45.99322 - LongLatHelper.longitudeNKilometersWest(0, -45, 110.567)) < 0.00001);
		//at -40 degrees (approx. over NZ)
		assertTrue(Math.abs(1.29656 - LongLatHelper.longitudeNKilometersEast(-40, 0, 110.567)) < 0.00001);
		assertTrue(Math.abs(-1.29656 - LongLatHelper.longitudeNKilometersWest(-40, 0, 110.567)) < 0.00001);
		assertTrue(Math.abs(-173.70344 - LongLatHelper.longitudeNKilometersEast(-40, -175, 110.567)) < 0.00001);
		assertTrue(Math.abs(-176.29656 - LongLatHelper.longitudeNKilometersWest(-40, -175, 110.567)) < 0.00001);
	}
}
