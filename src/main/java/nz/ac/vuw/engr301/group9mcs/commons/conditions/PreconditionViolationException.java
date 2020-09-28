package nz.ac.vuw.engr301.group9mcs.commons.conditions;

/**
 * For when the method arguments do not meet the contract of a method.
 * 
 * @author Claire
 */
public class PreconditionViolationException extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 764236203625490841L;

	/**
	 * 
	 */
	public PreconditionViolationException() {}

	/**
	 * @param message
	 */
	public PreconditionViolationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PreconditionViolationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PreconditionViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PreconditionViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
