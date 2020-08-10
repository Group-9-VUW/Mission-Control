package nz.ac.vuw.engr301.group9mcs.commons;

/**
 * Invariant violation exception, for when an internal method/class state does not
 * meet design specifications 
 * 
 * @author Claire
 */
public class InvariantViolationException extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 764236203625490841L;

	/**
	 * 
	 */
	public InvariantViolationException() {}

	/**
	 * @param message
	 */
	public InvariantViolationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvariantViolationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvariantViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvariantViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
