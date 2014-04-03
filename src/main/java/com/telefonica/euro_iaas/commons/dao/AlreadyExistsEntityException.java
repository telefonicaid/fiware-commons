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
