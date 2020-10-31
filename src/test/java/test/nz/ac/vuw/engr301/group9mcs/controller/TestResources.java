package test.nz.ac.vuw.engr301.group9mcs.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.engr301.group9mcs.controller.Resources;

/**
 * Tests for the Resource Object
 *
 * @author Bryony Gatehouse
 * Copyright (C) 2020, Mission Control Group 9
 */
public final class TestResources {

	/**
	 * Resource Object to test
	 */
	@SuppressWarnings("null")
	private Resources res;

	/**
	 * Create new versions of variables
	 */
	@SuppressWarnings("null")
	private void setup() {
		try {
			this.res = new Resources(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check that frame is returned (passed as null).
	 */
	@Test
	public void testFrameAddedReturned() {
		try {
			JFrame f = new JFrame();
			f.setName("frame");
			this.res = new Resources(f);
			assertTrue(this.res.getFrame().getName().equals("frame"));
		} catch (@SuppressWarnings("unused") HeadlessException e) {
			System.out.println("Screen not Connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test Longitude
	 */
	@Test
	public void testLongitude() {
		setup();
		assumeTrue(this.res != null);
		this.res.setLongitude(2.00);
		assertTrue(this.res.getLongitude() == 2.00);
	}

	/**
	 * Test Latitude
	 */
	@Test
	public void testLatitude() {
		setup();
		assumeTrue(this.res != null);
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
		assumeTrue(this.res != null);
		assertTrue(this.res.getDriver() != null);
	}

}
