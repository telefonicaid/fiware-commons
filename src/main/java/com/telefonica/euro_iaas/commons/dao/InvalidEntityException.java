/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.dao;

/**
 * Exception that will be launched when an invalid entity is persisted. An invalid entity is: <li>
 * <ul>
 * An entity with a not nullable field set to null
 * </ul>
 * <ul>
 * An entity with a duplicated value of an unique field
 * </ul>
 * </li>
 */
@SuppressWarnings("serial")
public class InvalidEntityException extends Exception {

    private Object entity;

    /**
     * Constructor of the class.
     * 
     * @param entity
     *            The requested entity
     */
    public InvalidEntityException(Object entity) {
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
    public InvalidEntityException(Object entity, Exception cause) {
        super(cause);
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "Trying to persist an invalid " + entity.getClass().getName() + " entity. Caused by: " + getCause();
    }

}
