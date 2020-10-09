package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;

/**
 * Tests for the LaunchRodData class.
 *
 * @author Joshua
 */
public class TestLaunchRodData {

	/**
	 * Tests creating a LaunchRodData instance, and getting its values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLaunchRodData_01() {
		LaunchRodData dat = new LaunchRodData(0, 0, 0);
		assertEquals(0, dat.getAngle());
		assertEquals(0, dat.getDirection());
		assertEquals(0, dat.getHeight());
	}

	/**
	 * Tests creating a LaunchRodData instance, and getting its values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLaunchRodData_02() {
		LaunchRodData dat = new LaunchRodData(12.56, 52.3, 0.45);
		assertEquals(12.56, dat.getAngle());
		assertEquals(52.3, dat.getDirection());
		assertEquals(0.45, dat.getHeight());
	}

	/**
	 * Tests creating a LaunchRodData instance, and getting its values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLaunchRodData_03() {
		LaunchRodData dat = new LaunchRodData(-12.56, -52.3, -0.45);
		assertEquals(-12.56, dat.getAngle());
		assertEquals(-52.3, dat.getDirection());
		assertEquals(-0.45, dat.getHeight());
	}
}
