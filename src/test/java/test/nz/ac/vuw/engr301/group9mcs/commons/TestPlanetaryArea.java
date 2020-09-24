package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.PlanetaryArea;

/**
 * Tests for the PlanetaryArea class.
 * 
 * @author Joshua
 */
public class TestPlanetaryArea {

	/**
	 * Tests that two identical PlanetaryAreas are equal.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPlanetaryAreaIsEqual() {
		PlanetaryArea area1 = PlanetaryArea.fromCorners(-20, 160, -40, 180);
		PlanetaryArea area2 = PlanetaryArea.fromCorners(-20, 160, -40, 180);
		area1.clip(area2);
		area2.scale(1);
		assertTrue(area1.equals(area1));
		assertTrue(area1.equals(area2));
		assertEquals(area1.hashCode(), area2.hashCode());
	}
}
