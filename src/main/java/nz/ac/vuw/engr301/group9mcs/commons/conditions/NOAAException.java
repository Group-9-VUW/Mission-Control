package nz.ac.vuw.engr301.group9mcs.commons.conditions;

/**
 * Thrown if NOAA not available
 *
 * @author Sai Panda
 * Copyright (C) 2020, Mission Control Group 9
 */
public class NOAAException extends Exception {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1;

    /**
     * Empty constructor.
     */
    public NOAAException() {}

    /**
     * Constructor with a message.
     * @param message the detail of the error (i.e. "Could not connect to NOAA")
     */
    public NOAAException(String message) {
        super(message);
    }

    /**
     * Constructor with a cause.
     * @param cause the cause of the exception.
     */
    public NOAAException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with both a message and throwable.
     * @param message the details of the error (i.e. "Could not connect to NOAA")
     * @param cause the cause of the exception.
     */
    public NOAAException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with all fields.
     * @param message the details of the error (i.e. "Could not connect to NOAA")
     * @param cause the cause of the Exception.
     * @param enableSuppression boolean to suppress the error
     * @param writableStackTrace boolean if the stack trace is writable.
     */
    public NOAAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
