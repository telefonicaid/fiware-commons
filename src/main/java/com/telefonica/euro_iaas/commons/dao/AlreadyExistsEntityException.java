/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.dao;

/**
 * This exception will be launched when a duplicated entity is persisted.
 */
@SuppressWarnings("serial")
public class AlreadyExistsEntityException extends Exception {

    private Object entity;

    /**
     * Constructor of the class.
     * 
     * @param entity
     *            The requested entity
     */
    public AlreadyExistsEntityException(Object entity) {
        this.entity = entity;
    }

    /**
     * Constructor of the class.
     * 
     * @param entity
     *            The requested entity
     * @param cause
     *            Parent exception
     */
    public AlreadyExistsEntityException(Object entity, Exception cause) {
        super(cause);
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "Trying to persist a duplicated " + entity.getClass().getName() + " entity. Caused by: " + getCause();
    }

}
