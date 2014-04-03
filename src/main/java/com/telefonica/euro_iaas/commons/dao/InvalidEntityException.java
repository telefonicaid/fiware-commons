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
        return "Trying to persist an invalid " + entity.getClass().getSimpleName() + " entity. Caused by: "
                + getCause();
    }

}
