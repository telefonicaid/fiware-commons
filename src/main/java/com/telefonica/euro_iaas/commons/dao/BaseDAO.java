package com.telefonica.euro_iaas.commons.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Base DAO interface, with common methods.
 *
 * @author Sergio Arroyo
 *
 * @param <T> Entity class of the entity
 * @param <ID> Class of the business key of the entity
 */
public interface BaseDAO<T, ID extends Serializable> {

    /**
     * Creates a new entity.
     *
     * @param entity Entity to be persisted
     * @return The entity just created.
     * @throws InvalidEntityException    If the entity is invalid (mandatory fields
     *                                   not present, duplicated unique fields, etc.)
     * @throws AlreadyExistsEntityException If an entity with the same id already
     *                                   exists in the repository
     */
    T create(T entity) throws InvalidEntityException, AlreadyExistsEntityException;

    /**
     * Updates the given entity.
     *
     * @return The entity just updated.
     *
     * @throws InvalidEntityException   If the entity is invalid (mandatory fields
     *                                  not set, duplicated unique fields, etc.) or
     *                                  if does not exists.
     */
    T update(T entity) throws InvalidEntityException;

    /**
     * Removes an existent entity.
     *
     * @param entity The entity to be removed
     */
    void remove(T entity);

    /**
     * Loads a previously created entity.
     *
     * @param id    Bussiness key of the entity to be retrieved
     * @return the found entity instance
     * @throws EntityNotFoundException If no entity is found with the given id
     */
    T load(ID id) throws EntityNotFoundException;

    /**
     * Find all entities of the given class.
     *
     * @return A list with all the entities of the given class. If no entities
     *         are found an empty list is returned
     */
    List<T> findAll();

}