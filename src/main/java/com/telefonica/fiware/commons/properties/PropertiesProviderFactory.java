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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Creates PropertiesUtils objects.
 */
public interface PropertiesProviderFactory {

    /**
     * Creates a properties util object.
     * <p>
     * The returned properties util is <b>NOT</b> transactional, so clients should handle transactions manually.
     * </p>
     * 
     * @param em
     *            Entity manager needed to connect with the underlying database
     * @return A valid <b>NON</b> transactional properties util object.
     */
    PropertiesProvider createPropertiesProvider(EntityManager em);

    /**
     * Creates a properties util object.
     * <p>
     * The returned properties util <b>IS</b> transactional. Transactional properties utils open new transaction (or
     * attach to existing transactions) for every call to store method.
     * </p>
     * 
     * @param emf
     *            Entity manager factory that provides access to the entity manager
     * @return A valid transactional properties util object.
     */
    PropertiesProvider createPropertiesProvider(EntityManagerFactory emf);

}
