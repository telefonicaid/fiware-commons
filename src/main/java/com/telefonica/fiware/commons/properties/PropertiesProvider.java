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

package com.telefonica.fiware.commons.properties;

import java.util.List;
import java.util.Properties;

/**
 * This interface gives us a way to manage pairs of properties (key,value) Reads properties from a filename and store
 * them on the database.
 */
public interface PropertiesProvider {

    /**
     * This method load an object with all the properties we have associated to the namespace.
     * <p>
     * If there is a file with the same name as the namespace (in the path) first we make a merge with the ones saved on
     * the database and then overwrite the values from the file with the ones load from the database.
     * </p>
     * 
     * @param namespace
     *            The namespace of the properties to be load (also the filename)
     * @return a Properties Object with all the properties that belongs to the namespace If any error happens
     */
    Properties load(final String namespace);

    /**
     * This method save the Properties object into the database.
     * 
     * @param properties
     *            Object to save on the database
     * @param namespace
     *            The namespace of the object
     */
    void store(final Properties properties, final String namespace);

    /**
     * Get the list with every namespaces.
     * 
     * @return the list of namespaces.
     */
    List<String> getNamespaces();

}
