/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U <br>
 * This file is part of FI-WARE project.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 * </p>
 * <p>
 * You may obtain a copy of the License at:<br>
 * <br>
 * http://www.apache.org/licenses/LICENSE-2.0
 * </p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * </p>
 * <p>
 * See the License for the specific language governing permissions and limitations under the License.
 * </p>
 * <p>
 * For those usages not covered by the Apache version 2.0 License please contact with opensource@tid.es
 * </p>
 */

package com.telefonica.euro_iaas.commons.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Base DAO interface, with common methods.
 * 
 * @author Sergio Arroyo
 * @param <T>
 *            Entity class of the entity
 * @param <ID>
 *            Class of the business key of the entity
 */
public interface BaseDAO<T, ID extends Serializable> {

    /**
     * Creates a new entity.
     * 
     * @param entity
     *            Entity to be persisted
     * @return The entity just created.
     * @throws InvalidEntityException
     *             If the entity is invalid (mandatory fields not present, duplicated unique fields, etc.)
     * @throws AlreadyExistsEntityException
     *             If an entity with the same id already exists in the repository
     */
    T create(T entity) throws AlreadyExistsEntityException;

    /**
     * Updates the given entity.
     * 
     * @return The entity just updated.
     * @throws InvalidEntityException
     *             If the entity is invalid (mandatory fields not set, duplicated unique fields, etc.) or if does not
     *             exists.
     */
    T update(T entity);

    /**
     * Removes an existent entity.
     * 
     * @param entity
     *            The entity to be removed
     */
    void remove(T entity);

    /**
     * Loads a previously created entity.
     * 
     * @param id
     *            Bussiness key of the entity to be retrieved
     * @return the found entity instance
     * @throws EntityNotFoundException
     *             If no entity is found with the given id
     */
    T load(ID id) throws EntityNotFoundException;

    /**
     * Find all entities of the given class.
     * 
     * @return A list with all the entities of the given class. If no entities are found an empty list is returned
     */
    List<T> findAll();

}
