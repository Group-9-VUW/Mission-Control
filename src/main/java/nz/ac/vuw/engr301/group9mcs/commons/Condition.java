package nz.ac.vuw.engr301.group9mcs.commons;

import java.util.Collection;
import java.util.function.Function;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A helper class for managing function conditions.
 * 
 * @author Claire
 */
public enum Condition {
	
	/**
	 * Preconditions, used for when method parameters are incorrect
	 */
	PRE((s) -> { return new PreconditionViolationException(s); }), 
	
	/**
	 * Postcondition, used for when method returns do not satisfy contracts.
	 */
	POST((s) -> { return new PostconditionViolationException(s); }), 
	
	/**
	 * Invariants, used for when method internal state does not satisfy contracts
	 */
	INVARIANT((s) -> { return new InvariantViolationException(s); });
	
	private final Function<String, Error> function;
	
	Condition(Function<String, Error> func)
	{
		this.function = func;
	}
	
	/**
	 * Checks that object <code>o</code> in non null. If it is, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param o the object
	 */
	public void nonNull(String name, @Nullable Object o)
	{
		if(o == null) {
			throw this.function.apply("Object " + name + " was null, non-null expected.");
		}
	}
	
	/**
	 * Checks the integer <code>i</code> is positive. If it isn't, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param i the integer
	 */
	public void positive(String name, int i)
	{
		if(i <= 0) {
			throw this.function.apply("Integer " + name + " was less than or equal to zero, positive expected.");
		}
	}
	
	/**
	 * Checks the integer <code>i</code> is negative. If it isn't, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param i the integer
	 */
	public void negative(String name, int i)
	{
		if(i >= 0) {
			throw this.function.apply("Integer " + name + " was less than or equal to zero, positive expected.");
		}
	}
	
	/**
	 * Checks the integer <code>i</code> is nonzero. If it isn't, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param i the integer
	 */
	public void nonzero(String name, int i)
	{
		if(i == 0) {
			throw this.function.apply("Integer " + name + " was zero, nonzero expected.");
		}
	}
	
	/**
	 * Checks the collection <code>i</code> is empty. If it isn't, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param collection the collection
	 */
	public void empty(String name, Collection<Object> collection)
	{
		if(collection.size() != 0) {
			throw this.function.apply("Collection " + name + " was not an empty set.");
		}
	}
	
	/**
	 * Checks the collection <code>i</code> is empty. If it is, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param collection the collection
	 */
	public void notEmpty(String name, Collection<Object> collection)
	{
		if(collection.size() == 0) {
			throw this.function.apply("Collection " + name + " was an empty set, non empty expected.");
		}
	}
	
	/**
	 * Checks the collection <code>i</code> is contains an object. If it doesn't, it throws an exception.
	 * 
	 * @param name The name of the object
	 * @param collection the collection
	 * @param o the object we're looking for
	 */
	public void contains(String name, Collection<Object> collection, Object o)
	{
		if(!collection.contains(o)) {
			throw this.function.apply("Collection " + name + " was not an empty set.");
		}
	}

}

