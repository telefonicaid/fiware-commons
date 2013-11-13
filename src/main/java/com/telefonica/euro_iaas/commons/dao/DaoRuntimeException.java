package com.telefonica.euro_iaas.commons.dao;

/**
 * Generic exception that is launched whenever a runtime error is launched while using the <code>PlayerDao</code>.
 */
@SuppressWarnings("serial")
public class DaoRuntimeException extends RuntimeException {

    /**
     * Constructor of the class.
     * 
     * @param cause
     *            Problem that caused the exception
     */
    public DaoRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor of the class.
     * 
     * @param message
     *            Message describing the problem
     * @param cause
     *            Problem that caused the exception
     */
    public DaoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
