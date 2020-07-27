/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.commons;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Claire
 *
 * Simple helper class to deal with library methods that don't use annotations
 */
public class Null {
	
	@NonNull
	public static <T> T nonNull(@Nullable T t)
	{
		if(t == null) 
			throw new PreconditionViolationException("Incorrect assumption, t was actually null.");
		return t;
	}

}
