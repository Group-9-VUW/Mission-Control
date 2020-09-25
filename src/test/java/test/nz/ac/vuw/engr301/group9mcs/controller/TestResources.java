package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.controller.Resources;

/**
 * Tests for the Resource Object
 * 
 * @author Bryony
 *
 */
public final class TestResources {

	/**
	 * Resource Object to test
	 */
	private Resources res;
	
	/**
	 * Create new versions of variables
	 */
	private void setup() {
		FakeJFrame f = new FakeJFrame("frame");
		this.res = new Resources(f);
	}
	
	/**
	 * Check that frame is returned (passed as null).
	 */
	@Test
	public void testFrameAddedReturned() {
		setup();
		assertTrue(this.res.getFrame().getName().equals("frame"));
	}
	
	/**
	 * Test Longitude
	 */
	@Test
	public void testLongitude() {
		setup();
		this.res.setLongitude(2.00);
		assertTrue(this.res.getLongitude() == 2.00);
	}
	
	/**
	 * Test Latitude
	 */
	@Test
	public void testLatitude() {
		setup();
		this.res.setLatitude(2.00);
		assertTrue(this.res.getLatitude() == 2.00);
	}
	
	/**
	 * Test LORADriver ...??
	 */
	@SuppressWarnings("null")
	@Test
	public void testLora() {
		setup();
		assertTrue(this.res.getDriver() != null);
	}
	
}
