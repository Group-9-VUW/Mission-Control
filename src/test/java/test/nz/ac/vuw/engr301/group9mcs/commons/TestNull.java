package test.nz.ac.vuw.engr301.group9mcs.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;

/**
 * Tests the Null class.
 *
 * @author Joshua
 */
public class TestNull {

	/**
	 * Tests different variable types with nonNull values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNonNull() {
		Null n = new Null();
		assertNotNull(n);
		@Nullable Double dou = 0.5;
		@Nullable String str = "hello";
		@Nullable List<@Nullable Integer> intList = Arrays.asList(1, 2, 3, null);
		assertEquals(0.5, Null.nonNull(dou));
		assertEquals("hello", Null.nonNull(str));
		//returns @NonNull List<@Nullable Integer>
		assertEquals(Arrays.asList(1, 2, 3, null), Null.nonNull(intList));
	}

	/**
	 * Tests that PreconditionViolationExceptions are thrown for null values.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNull() {
		@Nullable Double dou = null;
		@Nullable String str = null;
		@Nullable List<@Nullable Integer> intList = null;
		assertThrows(PreconditionViolationException.class, () -> {Null.nonNull(dou);});
		assertThrows(PreconditionViolationException.class, () -> {Null.nonNull(str);});
		assertThrows(PreconditionViolationException.class, () -> {Null.nonNull(intList);});
	}
}
