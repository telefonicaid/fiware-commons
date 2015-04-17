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

package com.telefonica.fiware.commons.dao;

/**
 *
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends Exception {

    private String errorMsg;

    /**
     * Constructor of the class.
     * 
     * @param entity
     *            The requested entity
     * @param primaryKeyFieldName
     *            The name of the primary key attribute
     * @param primaryKeyFieldValue
     *            The value of the primary key attribute
     */
    @SuppressWarnings("unchecked")
    public EntityNotFoundException(Class entity, String primaryKeyFieldName, Object primaryKeyFieldValue) {
        this(entity, new String[] { primaryKeyFieldName }, new Object[] { primaryKeyFieldValue });
    }

    /**
     * Constructor of the class.
     * 
     * @param entity
     *            The requested entity
     * @param primaryKeyFieldNames
     *            The names of all the primary key fields
     * @param primaryKeyFieldValues
     *            The values of all the primary key fields
     */
    @SuppressWarnings("unchecked")
    public EntityNotFoundException(Class entity, String[] primaryKeyFieldNames, Object[] primaryKeyFieldValues) {

        StringBuffer errorMessage = new StringBuffer("No entity " + entity.getSimpleName()
                + " found with the following criteria: [");

        int i;
        for (i = 0; i < primaryKeyFieldNames.length - 1; i++) {
            errorMessage.append(primaryKeyFieldNames[i]);
            errorMessage.append(" = ");
            errorMessage.append(primaryKeyFieldValues[i]);
            errorMessage.append(", ");
        }
        errorMessage.append(primaryKeyFieldNames[i]);
        errorMessage.append(" = ");
        errorMessage.append(primaryKeyFieldValues[i]);
        errorMessage.append("].");

        if (getCause() != null) {
            errorMessage.append(" Caused by: ");
            errorMessage.append(getCause().getMessage());
        }

        this.errorMsg = errorMessage.toString();
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

}
