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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

public class PropertiesProviderImplTest {

    /**
     * Should get all namespaces.
     */
    @Test
    public void shouldGetAllNamespaces() {
        // given
        PropertiesDAO propertiesDAO = mock(PropertiesDAO.class);
        PropertiesProviderImpl propertiesProviderImpl = new PropertiesProviderImpl(propertiesDAO);
        List<String> list = new ArrayList<String>();
        list.add("filename.properties");
        when(propertiesDAO.findNamespaces()).thenReturn(list);
        // when

        List namespaces = propertiesProviderImpl.getNamespaces();

        // then
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("filename.properties", namespaces.get(0));
    }

    /**
     * Should store a set of properties
     */
    @Test
    public void shouldStoreProperties() {
        // given
        PropertiesDAO propertiesDAO = mock(PropertiesDAO.class);
        PropertiesProviderImpl propertiesProviderImpl = new PropertiesProviderImpl(propertiesDAO);
        Properties properties = new Properties();
        properties.setProperty("key", "value");

        // when

        propertiesProviderImpl.store(properties, "filename.properties");

        // then
        verify(propertiesDAO).store(properties, "filename.properties");
    }

    /**
     * Should load properties
     */
    @Test
    public void shouldLoadProperties() {
        // given
        PropertiesDAO propertiesDAO = mock(PropertiesDAO.class);
        PropertiesProviderImpl propertiesProviderImpl = new PropertiesProviderImpl(propertiesDAO);

        Properties properties = new Properties();
        properties.setProperty("test1.key", "value in database");
        when(propertiesDAO.load("/test1.properties")).thenReturn(properties);

        // when
        Properties resultProperties = propertiesProviderImpl.load("/test1.properties");

        // then
        assertNotNull(resultProperties);
        assertEquals("value in database", resultProperties.getProperty("test1.key"));
        assertEquals("test2.value", resultProperties.getProperty("test2.key"));
        assertEquals("test3.value", resultProperties.getProperty("test3.key"));
    }

    /**
     * Should load properties with invalid file
     */
    @Test
    public void shouldFailInLoadProperties() {
        // given
        PropertiesDAO propertiesDAO = mock(PropertiesDAO.class);
        PropertiesProviderImpl propertiesProviderImpl = new PropertiesProviderImpl(propertiesDAO);

        Properties properties = new Properties();
        when(propertiesDAO.load("/invalid.properties")).thenReturn(properties);

        // when
        Properties resultProperties = propertiesProviderImpl.load("/invalid.properties");

        // then
        assertNotNull(resultProperties);
        assertEquals(0, resultProperties.size());

    }

}
