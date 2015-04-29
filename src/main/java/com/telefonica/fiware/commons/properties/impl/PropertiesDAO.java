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

package com.telefonica.fiware.commons.properties.impl;

import java.util.List;
import java.util.Properties;

/**
 * DAO to load and store properties in a persistence environment.
 */
public interface PropertiesDAO {

    /**
     * Load the properties in the given namespace. If there are no properties defined in the given namespace, returns an
     * empty properties object.
     * 
     * @param namespace
     *            The namespace to be loaded
     * @return Properties for the given namespace
     */
    Properties load(final String namespace);

    /**
     * Store the properties in the persistence environment. Moreover, preserves the existing properties in the
     * persistence environment and only updates the modified (o newer) ones.
     * 
     * @param properties
     *            Properties to stored
     * @param namespace
     *            Associated namespace
     */
    void store(Properties properties, final String namespace);

    /**
     * Load every namespaces stored in DB.
     * 
     * @return the namespaces.
     */
    List<String> findNamespaces();

}
