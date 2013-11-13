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
