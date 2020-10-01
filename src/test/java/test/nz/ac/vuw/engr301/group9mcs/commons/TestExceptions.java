package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PostconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.InvariantViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.NOAAException;

/**
 * Tests creating new InvariantViolation, PostconditionViolation,
 * PreconditionViolation, and NOAA Exceptions.
 *
 * @author Joshua
 */
public class TestExceptions {

	/**
	 * Tests the PreconditionViolationException class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPreconditionViolationExceptions() {
		PreconditionViolationException e;
		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {throw new PreconditionViolationException();}));
		assertNull(e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {throw new PreconditionViolationException("err");}));
		assertEquals("err", e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {throw new PreconditionViolationException(new Throwable());}));
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {throw new PreconditionViolationException("err", new Throwable());}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(PreconditionViolationException.class, () -> {throw new PreconditionViolationException("err", new Throwable(), true, false);}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());
		assertEquals(0, e.getStackTrace().length);
	}

	/**
	 * Tests the PostconditionViolationException class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPostconditionViolationExceptions() {
		PostconditionViolationException e;
		e = Null.nonNull(assertThrows(PostconditionViolationException.class, () -> {throw new PostconditionViolationException();}));
		assertNull(e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(PostconditionViolationException.class, () -> {throw new PostconditionViolationException("err");}));
		assertEquals("err", e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(PostconditionViolationException.class, () -> {throw new PostconditionViolationException(new Throwable());}));
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(PostconditionViolationException.class, () -> {
			throw new PostconditionViolationException("err", new Throwable());}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(PostconditionViolationException.class, () -> {
			throw new PostconditionViolationException("err", new Throwable(), true, false);}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());
		assertEquals(0, e.getStackTrace().length);
	}
	
	/**
	 * Tests the InvariantViolationException class.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testInvariantViolationExceptions() {
		InvariantViolationException e;
		e = Null.nonNull(assertThrows(InvariantViolationException.class, () -> {throw new InvariantViolationException();}));
		assertNull(e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(InvariantViolationException.class, () -> {throw new InvariantViolationException("err");}));
		assertEquals("err", e.getMessage());
		assertNull(e.getCause());

		e = Null.nonNull(assertThrows(InvariantViolationException.class, () -> {throw new InvariantViolationException(new Throwable());}));
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(InvariantViolationException.class, () -> {
			throw new InvariantViolationException("err", new Throwable());}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());

		e = Null.nonNull(assertThrows(InvariantViolationException.class, () -> {
			throw new InvariantViolationException("err", new Throwable(), true, false);}));
		assertEquals("err", e.getMessage());
		assertNotNull(e.getCause());
		assertNull(e.getCause().getMessage());
		assertEquals(0, e.getStackTrace().length);
	}
}
