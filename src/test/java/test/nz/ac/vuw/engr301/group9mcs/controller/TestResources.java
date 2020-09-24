package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.*;

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
	public void test() {
		setup();
		assertTrue(this.res.getFrame().getName().equals("frame"));
	}
	
}
