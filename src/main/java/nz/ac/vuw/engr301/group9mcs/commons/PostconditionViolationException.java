package nz.ac.vuw.engr301.group9mcs.commons;

public class PostconditionViolationException extends Error {

	private static final long serialVersionUID = 764236203625490841L;

	public PostconditionViolationException() {}

	public PostconditionViolationException(String message) {
		super(message);
	}

	public PostconditionViolationException(Throwable cause) {
		super(cause);
	}

	public PostconditionViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PostconditionViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
