package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.map.PlanetaryArea;

/**
 * Tests for the PlanetaryArea class.
 *
 * @author Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestPlanetaryArea {

	/**
	 * Tests that two identical PlanetaryAreas are equal.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPlanetaryAreaIsEqual() {
		PlanetaryArea area1 = PlanetaryArea.fromCorners(-20, 160, -40, 180);
		PlanetaryArea area2 = PlanetaryArea.fromCenter(-30, 170, 10, 10);
		assertTrue(area1.equals(area1));
		assertTrue(area1.equals(area2));
		assertEquals(area1.hashCode(), area2.hashCode());
	}

	/**
	 * Tests that two different PlanetaryAreas are not equal.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testIsNotEquals() {
		PlanetaryArea area = PlanetaryArea.fromCenter(-30, 160, 10, 20);
		//test non-PlanetaryArea
		assertFalse(area.equals(null));
		assertFalse(area.equals(new Object()));
		//test different center latitude
		PlanetaryArea diffArea = PlanetaryArea.fromCenter(-20, 160, 10, 20);
		assertFalse(area.equals(diffArea));
		//test different center longitude
		diffArea = PlanetaryArea.fromCenter(-30, 150, 10, 20);
		assertFalse(area.equals(diffArea));
		//test different latitudinal radius
		diffArea = PlanetaryArea.fromCenter(-30, 160, 15, 20);
		assertFalse(area.equals(diffArea));
		//test different longitudinal radius
		diffArea = PlanetaryArea.fromCenter(-30, 160, 10, 15);
		assertFalse(area.equals(diffArea));
	}

	/**
	 * Tests all the getter methods.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGettingAttributes() {
		PlanetaryArea area1 = PlanetaryArea.fromCorners(-20, 160, -40, 180);
		PlanetaryArea area2 = PlanetaryArea.fromCenter(-30, 170, 10, 10);
		//test the upper left latitudes and longitudes
		assertEquals(-20, area1.getUpperLeftLatitude());
		assertEquals(-20, area2.getUpperLeftLatitude());
		assertEquals(160, area1.getUpperLeftLongitude());
		assertEquals(160, area2.getUpperLeftLongitude());
		//test the bottom right latitudes and longitudes
		assertEquals(-40, area1.getBottomRightLatitude());
		assertEquals(-40, area2.getBottomRightLatitude());
		assertEquals(180, area1.getBottomRightLongitude());
		assertEquals(180, area2.getBottomRightLongitude());
		//test the center latitudes and longitudes
		assertEquals(-30, area1.getLat());
		assertEquals(-30, area2.getLat());
		assertEquals(170, area1.getLon());
		assertEquals(170, area2.getLon());
		//test the radii
		assertEquals(10, area1.getRadLat());
		assertEquals(10, area2.getRadLat());
		assertEquals(10, area1.getRadLon());
		assertEquals(10, area2.getRadLon());
	}

	/**
	 * Tests scaling a PlanetaryArea by different amounts.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testScale() {
		PlanetaryArea area = PlanetaryArea.fromCenter(-20, 160, 10, 10);
		//scale by 1 (no scaling)
		PlanetaryArea scaled = area.scale(1);
		assertEquals(10, scaled.getRadLat());
		assertEquals(10, scaled.getRadLon());
		assertEquals(-10, scaled.getUpperLeftLatitude());
		assertEquals(150, scaled.getUpperLeftLongitude());
		//scale by 0.5 (halve width and height)
		scaled = area.scale(0.5);
		assertEquals(5, scaled.getRadLat());
		assertEquals(5, scaled.getRadLon());
		assertEquals(-15, scaled.getUpperLeftLatitude());
		assertEquals(155, scaled.getUpperLeftLongitude());
		//scale by 2 (double width and height)
		scaled = area.scale(2);
		assertEquals(20, scaled.getRadLat());
		assertEquals(20, scaled.getRadLon());
		assertEquals(0, scaled.getUpperLeftLatitude());
		assertEquals(140, scaled.getUpperLeftLongitude());
	}

	/**
	 * Tests creating invalid PlanetaryAreas.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testInvalidAreas() {
		//invalid creation of PlanetaryArea from corners
		PreconditionViolationException e = Null.nonNull(assertThrows(PreconditionViolationException.class,
				() -> {PlanetaryArea.fromCorners(-20, 140, -10, 150);}));
		assertEquals("Invalid planetary area specification, upper latitude should be larger than lower latitude", e.getMessage());
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {PlanetaryArea.fromCorners(-20, 140, -30, 130);}));
		//invalid creation of PlanetaryArea from center
		assertEquals("Invalid planetary area specification, left longitude should be smaller than right longitude", e.getMessage());
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {PlanetaryArea.fromCenter(-20, 140, -20, 20);}));
		assertEquals("Integer latRadius was less than or equal to zero, positive expected.", e.getMessage());
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {PlanetaryArea.fromCenter(-20, 140, 20, -20);}));
		assertEquals("Integer lonRadius was less than or equal to zero, positive expected.", e.getMessage());
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {PlanetaryArea.fromCenter(-20, 140, 0, 20);}));
		assertEquals("Integer latRadius was less than or equal to zero, positive expected.", e.getMessage());
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {PlanetaryArea.fromCenter(-20, 140, 20, 0);}));
		assertEquals("Integer lonRadius was less than or equal to zero, positive expected.", e.getMessage());
	}

	/**
	 * Tests clipping PlanetaryAreas.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testClip() {
		//clip the original area
		PlanetaryArea core = PlanetaryArea.fromCorners(-20, 150, -40, 170);
		PlanetaryArea toClip = PlanetaryArea.fromCorners(-10, 140, -50, 180);
		PlanetaryArea result = core.clip(toClip);
		assertEquals(-20, result.getUpperLeftLatitude());
		assertEquals(150, result.getUpperLeftLongitude());
		assertEquals(-40, result.getBottomRightLatitude());
		assertEquals(170, result.getBottomRightLongitude());

		//clip with lower upperLeftLatitude
		toClip = PlanetaryArea.fromCorners(-30, 140, -50, 180);
		result = core.clip(toClip);
		assertEquals(-30, result.getUpperLeftLatitude());
		assertEquals(150, result.getUpperLeftLongitude());
		assertEquals(-40, result.getBottomRightLatitude());
		assertEquals(170, result.getBottomRightLongitude());

		//clip with lower upperLeftLongitude
		toClip = PlanetaryArea.fromCorners(-10, 160, -50, 180);
		result = core.clip(toClip);
		assertEquals(-20, result.getUpperLeftLatitude());
		assertEquals(160, result.getUpperLeftLongitude());
		assertEquals(-40, result.getBottomRightLatitude());
		assertEquals(170, result.getBottomRightLongitude());

		//clip with lower bottomRightLatitude
		toClip = PlanetaryArea.fromCorners(-10, 140, -30, 180);
		result = core.clip(toClip);
		assertEquals(-20, result.getUpperLeftLatitude());
		assertEquals(150, result.getUpperLeftLongitude());
		assertEquals(-30, result.getBottomRightLatitude());
		assertEquals(170, result.getBottomRightLongitude());

		//clip with lower bottomRightLongitude
		toClip = PlanetaryArea.fromCorners(-10, 140, -50, 160);
		result = core.clip(toClip);
		assertEquals(-20, result.getUpperLeftLatitude());
		assertEquals(150, result.getUpperLeftLongitude());
		assertEquals(-40, result.getBottomRightLatitude());
		assertEquals(160, result.getBottomRightLongitude());
	}

	/**
	 * Tests the containsArea method of the PlanetaryArea class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testContainsArea() {
		PlanetaryArea core = PlanetaryArea.fromCorners(-20, 150, -40, 170);
		assertTrue(core.containsArea(PlanetaryArea.fromCorners(-20, 150, -40, 170)));
		//upperLeftLatitude is not contained
		assertFalse(core.containsArea(PlanetaryArea.fromCorners(-10, 150, -40, 170)));
		//upperLeftLongitude is not contained
		assertFalse(core.containsArea(PlanetaryArea.fromCorners(-20, 140, -40, 170)));
		//bottomRightLatitude is not contained
		assertFalse(core.containsArea(PlanetaryArea.fromCorners(-20, 150, -50, 170)));
		//bottomRightLongitude is not contained
		assertFalse(core.containsArea(PlanetaryArea.fromCorners(-20, 150, -40, 180)));
	}

	/**
	 * Tests the overlapsWithArea method of the PlanetaryArea class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testOverlapsWithArea() {
		PlanetaryArea core = PlanetaryArea.fromCorners(-20, 150, -40, 170);
		//no overlap
		assertFalse(core.overlapsWithArea(PlanetaryArea.fromCorners(-10, 120, -15, 130)));
		//overlap
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-45, 175, 10, 10)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-45, 145, 10, 10)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-15, 145, 10, 10)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-15, 175, 10, 10)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-15, 160, 20, 20)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-30, 180, 20, 20)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-45, 160, 20, 20)));
		assertTrue(core.overlapsWithArea(PlanetaryArea.fromCenter(-30, 140, 20, 20)));
	}
}
