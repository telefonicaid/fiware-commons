/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

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
