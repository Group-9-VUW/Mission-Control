/**
 *
 */
package nz.ac.vuw.engr301.group9mcs.commons.conditions;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Simple helper class to deal with library methods that don't use annotations
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class Null {

	/**
	 * Asserts an object as non null.
	 *
	 * @param <T> The type of the object
	 * @param t The object
	 * @throws PreconditionViolationException if object is null
	 * @return The object, if it is non null
	 */
	@NonNull
	public static <T> T nonNull(@Nullable T t)
	{
		if(t == null)
			throw new PreconditionViolationException("Incorrect assumption, t was actually null.");
		return t;
	}

}
