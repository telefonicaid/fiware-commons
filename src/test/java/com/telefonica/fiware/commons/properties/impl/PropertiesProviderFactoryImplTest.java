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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;

import com.telefonica.fiware.commons.properties.PropertiesProvider;

public class PropertiesProviderFactoryImplTest {

    /**
     * Should creates properties provider
     */
    @Test
    public void shouldCreatesPropertiesProvider() {
        // given
        PropertiesProviderFactoryImpl propertiesProviderFactoryImpl = new PropertiesProviderFactoryImpl();
        // when
        EntityManager entityManager = mock(EntityManager.class);
        PropertiesProvider propertiesProvider = propertiesProviderFactoryImpl.createPropertiesProvider(entityManager);

        // then
        assertNotNull(propertiesProvider);

    }

    /**
     * Should creates properties provider using entityManagerFactory
     */
    @Test
    public void shouldCreatesPropertiesProviderWithEntityManagerFactory() {
        // given
        PropertiesProviderFactoryImpl propertiesProviderFactoryImpl = new PropertiesProviderFactoryImpl();
        // when
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        PropertiesProvider propertiesProvider = propertiesProviderFactoryImpl
                .createPropertiesProvider(entityManagerFactory);

        // then
        assertNotNull(propertiesProvider);

    }

}
