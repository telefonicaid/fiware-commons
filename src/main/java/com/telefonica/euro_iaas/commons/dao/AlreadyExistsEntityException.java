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
     * @param entity The requested entity
     */
    public AlreadyExistsEntityException(Object entity) {
        this.entity = entity;
    }

    /**
     * Constructor of the class.
     *
     * @param entity The requested entity
     * @param cause Parent exception
     */
    public AlreadyExistsEntityException(Object entity, Exception cause) {
        super(cause);
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "Trying to persist a duplicated " + entity.getClass().getName() +
                " entity. Caused by: " + getCause();
    }

}
