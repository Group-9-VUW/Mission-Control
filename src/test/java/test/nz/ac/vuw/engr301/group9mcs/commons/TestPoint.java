package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

/**
 * Tests for the Point class.
 *
 * @author Joshua Hindley
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestPoint {


	/**
	 * Tests creating and getting a Point.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPoint() {
		Point p = new Point(-40, 170);
		assertEquals(-40, p.getLatitude());
		assertEquals(170, p.getLongitude());
	}

	/**
	 * Tests the equals method in the Point class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEquals() {
		Point p = new Point(-40, 170);
		Point equalP = new Point(-40, 170);
		Point diffLat = new Point(20, 170);
		Point diffLong = new Point(-40, 90);
		//equal points
		assertTrue(p.equals(equalP));
		assertTrue(equalP.equals(p));
		//different points
		assertFalse(p.equals(diffLat));
		assertFalse(p.equals(diffLong));
		//non point objects
		assertFalse(p.equals(null));
		assertFalse(p.equals(new Object()));
	}

	/**
	 * Tests the hashCode method in the Point class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		Point p = new Point(-40, 170);
		Point equalP = new Point(-40, 170);
		Point nonIntP = new Point(86.45, -128.45);
		//equal points
		assertEquals(p.hashCode(), equalP.hashCode());
		assertEquals(-1070, p.hashCode());
		//rounded
		assertEquals(2551, nonIntP.hashCode());

	}
}
