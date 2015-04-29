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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;

import com.telefonica.fiware.commons.properties.bean.PersistentProperty;

/**
 * Class for test PropertiesDAOJPAImpl
 */
public class PropertiesDAOJPAImplTest {
    /**
     * Should load properties from database
     */
    @Test
    public void shouldLoadPropertiesFromDatabase() {
        // given

        PersistentProperty persistentProperty1 = new PersistentProperty();
        persistentProperty1.setNamespace("filename.properties");
        persistentProperty1.setKey("username");
        persistentProperty1.setValue("value");
        List<PersistentProperty> list = new ArrayList<PersistentProperty>();

        list.add(persistentProperty1);

        EntityManager entityManager = mock(EntityManager.class);
        PropertiesDAO propertiesDAO = new PropertiesDAOJPAImpl(entityManager);

        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(list);
        // when
        Properties properties = propertiesDAO.load("file.properties");

        // then
        assertNotNull(properties);
        assertEquals("value", properties.getProperty("username"));
        verify(entityManager).createQuery(anyString());
        verify(query).getResultList();
    }

    /**
     * Should store property in database
     */
    @Test
    public void shouldStoreProperties() {
        // given

        EntityManager entityManager = mock(EntityManager.class);
        PropertiesDAO propertiesDAO = new PropertiesDAOJPAImpl(entityManager);

        PersistentProperty persistentProperty = mock(PersistentProperty.class);
        when(entityManager.merge(any(PersistentProperty.class))).thenReturn(persistentProperty);
        // when

        Properties properties = new Properties();
        properties.setProperty("p1", "v1");
        properties.setProperty("p2", "v2");
        propertiesDAO.store(properties, "file.properties");

        // then
        verify(entityManager, times(2)).merge(any(PersistentProperty.class));
    }

    /**
     * Should find namespace
     */
    @Test
    public void shouldFindNamespace() {
        // given

        EntityManager entityManager = mock(EntityManager.class);
        PropertiesDAOJPAImpl propertiesDAOJPAImpl = new PropertiesDAOJPAImpl();
        propertiesDAOJPAImpl.setEntityManager(entityManager);
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        List list = new ArrayList<String>();
        list.add("filename.properties");
        when(query.getResultList()).thenReturn(list);
        // when
        List result = propertiesDAOJPAImpl.findNamespaces();
        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("filename.properties", result.get(0));
    }

}
