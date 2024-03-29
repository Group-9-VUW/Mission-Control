package nz.ac.vuw.engr301.group9mcs.commons.conditions;

/**
 * For when the postcondition of a method is violated.
 *
 * @author Claire Chambers
 * Copyright (C) 2020, Mission Control Group 9
 */
public class PostconditionViolationException extends Error {

	/**
	 *
	 */
	private static final long serialVersionUID = 764236203625490841L;

	/**
	 *
	 */
	public PostconditionViolationException() {}

	/**
	 * @param message
	 */
	public PostconditionViolationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PostconditionViolationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PostconditionViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PostconditionViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}