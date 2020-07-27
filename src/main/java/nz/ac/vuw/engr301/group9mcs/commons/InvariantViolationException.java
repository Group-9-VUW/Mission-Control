package nz.ac.vuw.engr301.group9mcs.commons;

public class InvariantViolationException extends Error {

	private static final long serialVersionUID = 764236203625490841L;

	public InvariantViolationException() {}

	public InvariantViolationException(String message) {
		super(message);
	}

	public InvariantViolationException(Throwable cause) {
		super(cause);
	}

	public InvariantViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvariantViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
