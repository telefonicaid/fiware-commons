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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;

/**
 * Test class for PropertiesProviderTxImpl
 */
public class PropertiesProviderTxImplTest {

    /**
     * Should Load Properties
     */
    @Test
    public void shouldLoadProperties() {
        // given

        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        PropertiesProviderTxImpl propertiesProviderTxImpl = new PropertiesProviderTxImpl(entityManagerFactory);
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        List list = new ArrayList<String>();
        when(query.getResultList()).thenReturn(list);

        // when
        Properties properties = propertiesProviderTxImpl.load("filename.properties");

        // then
        assertNotNull(properties);
        verify(entityManagerFactory).createEntityManager();
    }

    /**
     * Should store properties
     */
    @Test
    public void shouldStoreProperties() {
        // given
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        PropertiesProviderTxImpl propertiesProviderTxImpl = new PropertiesProviderTxImpl(entityManagerFactory);
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        EntityTransaction entityTransaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        Properties properties = new Properties();
        properties.setProperty("key1", "value1");
        // when

        propertiesProviderTxImpl.store(properties, "filename.properties");

        // then
        verify(entityManagerFactory).createEntityManager();
        verify(entityManager).close();
        verify(entityTransaction).isActive();
        verify(entityTransaction).begin();
        verify(entityTransaction).commit();
    }

    /**
     * Should get namespaces
     */
    @Test
    public void shouldGetNameSpaces() {
        // given
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        PropertiesProviderTxImpl propertiesProviderTxImpl = new PropertiesProviderTxImpl(entityManagerFactory);
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        List list = new ArrayList<String>();
        list.add("filename.properties");
        when(query.getResultList()).thenReturn(list);
        // when

        List<String> resultList = propertiesProviderTxImpl.getNamespaces();
        // then
        assertNotNull(resultList);
        verify(entityManagerFactory).createEntityManager();
        verify(entityManager).close();
    }
}
