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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for abstract class AbstractBaseDAO.
 */
public class AbstractBaseDaoTest {

    /**
     * Should creates a entity.
     * 
     * @throws Exception
     */
    @Test
    public void shouldCreates() throws Exception {

        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        Object entity = mock(Object.class);
        // when
        abstractBaseDao.create(entity);

        // then
        verify(entityManager).persist(entity);
        verify(entityManager).flush();
    }

    /**
     * Should Throw an AlreadyExistsEntityException in create method when an entity already exists in database.
     * 
     * @throws Exception
     */
    @Test(expected = AlreadyExistsEntityException.class)
    public void shouldThrowExceptionWhenCreateWithAlreadyExistEntity() throws Exception {

        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        String entity = "Entity";
        doThrow(new EntityExistsException(entity)).when(entityManager).persist(entity);

        // when
        abstractBaseDao.create(entity);

        // then
    }

    /**
     * Should set optional pagination.
     */
    @Test
    public void shouldSetOptionalPagination() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        // when
        AbstractSearchCriteria searchCriteria = new AbstractSearchCriteria();
        Criteria baseCriteria = mock(Criteria.class);
        Criteria resultCriteria = abstractBaseDao.setOptionalPagination(searchCriteria, baseCriteria);

        // then
        assertNotNull(resultCriteria);

        verify(baseCriteria).addOrder(any(Order.class));
    }

    /**
     * Should set optional pagination with inverse order.
     */
    @Test
    public void shouldSetOptionalPaginationWithInverseOrder() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        AbstractSearchCriteria searchCriteria = new AbstractSearchCriteria();
        Criteria baseCriteria = mock(Criteria.class);
        searchCriteria.setPage(1);
        searchCriteria.setPageSize(2);

        // when
        Criteria resultCriteria = abstractBaseDao.setOptionalPagination(searchCriteria, baseCriteria, true);

        // then
        assertNotNull(resultCriteria);

        verify(baseCriteria).addOrder(any(Order.class));
    }

    /**
     * Should set pagination configuring page and page size.
     */
    @Test
    public void shouldSetPagination() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        AbstractSearchCriteria searchCriteria = new AbstractSearchCriteria(1, 2);
        Criteria baseCriteria = mock(Criteria.class);
        searchCriteria.setOrderBy("fieldName");

        // when
        Criteria resultCriteria = abstractBaseDao.setPagination(searchCriteria, baseCriteria);

        // then
        assertNotNull(resultCriteria);

        verify(baseCriteria).addOrder(any(Order.class));
        verify(baseCriteria).setMaxResults(2);
        verify(baseCriteria).setFirstResult(2);
    }

    /**
     * Should set pagination with inverse order and using default constructor.
     */
    @Test
    public void shouldSetPaginationWithInverseOrder() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        AbstractSearchCriteria searchCriteria = new AbstractSearchCriteria();
        Criteria baseCriteria = mock(Criteria.class);
        searchCriteria.setPage(1);
        searchCriteria.setPageSize(2);

        // when
        Criteria resultCriteria = abstractBaseDao.setPagination(searchCriteria, baseCriteria, true);

        // then
        assertNotNull(resultCriteria);

        verify(baseCriteria).addOrder(any(Order.class));
        verify(baseCriteria).setMaxResults(2);
        verify(baseCriteria).setFirstResult(2);
    }

    /**
     * Should throw IllegalArgumentException in method setPagination with invalid page argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldSetPaginationWithInvalidPage() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);

        AbstractSearchCriteria searchCriteria = new AbstractSearchCriteria(-12, 2, "name", "desc");
        Criteria baseCriteria = mock(Criteria.class);

        // when
        abstractBaseDao.setPagination(searchCriteria, baseCriteria);

    }

    /**
     * Should load entity by field.
     * 
     * @throws EntityNotFoundException
     */
    @Test
    public void shouldLoadByField() throws EntityNotFoundException {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);
        String entity = "Entity";
        String validQuery = MessageFormat.format("SELECT o FROM {0} o WHERE o.{1} = :{1}", new Object[] {
            entity.getClass().getName(), "name1" });

        Query query = mock(Query.class);
        when(entityManager.createQuery(validQuery)).thenReturn(query);
        when(query.setParameter("name1", "value")).thenReturn(query);
        Object resultMock = mock(Object.class);
        when(query.getSingleResult()).thenReturn(resultMock);

        // when
        Object result = abstractBaseDao.loadByField(entity.getClass(), "name1", "value");

        // then
        assertNotNull(result);
        verify(entityManager).createQuery(validQuery);
        verify(query).setParameter("name1", "value");
    }

    /**
     * Should throw an EntityNotFoundException when call to loadByField with and element that isn't in database.
     * 
     * @throws EntityNotFoundException
     */
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowNoResultExceptionInLoadByFieldWithNameNotFound() throws EntityNotFoundException {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);
        String entity = "Entity";
        String validQuery = MessageFormat.format("SELECT o FROM {0} o WHERE o.{1} = :{1}", new Object[] {
            entity.getClass().getName(), "name1" });

        Query query = mock(Query.class);
        when(entityManager.createQuery(validQuery)).thenReturn(query);
        when(query.setParameter("name1", "value")).thenReturn(query);
        Object resultMock = mock(Object.class);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        // when
        abstractBaseDao.loadByField(entity.getClass(), "name1", "value");

        // then

    }

    /**
     * Should update an entity.
     */
    @Test
    public void shouldUpdate() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);
        // when

        Object entity = new Object();
        abstractBaseDao.update(entity);

        // then
        verify(entityManager).merge(entity);
        verify(entityManager).flush();
    }

    /**
     * Should remove an entity.
     */
    @Test
    public void shouldRemove() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);
        Object entity = new Object();
        // when
        when(entityManager.contains(entity)).thenReturn(true);

        abstractBaseDao.remove(entity);

        // then
        verify(entityManager).contains(entity);
        verify(entityManager).remove(entity);
        verify(entityManager).flush();
    }

    /**
     * Should remove an entity although don't exist session.
     */
    @Test
    public void shouldRemoveWithoutSession() {
        // given
        AbstractBaseDao abstractBaseDao = mock(AbstractBaseDao.class, Mockito.CALLS_REAL_METHODS);
        EntityManager entityManager = mock(EntityManager.class);
        abstractBaseDao.setEntityManager(entityManager);
        Object entity = new Object();
        // when
        when(entityManager.contains(entity)).thenReturn(false);
        when(entityManager.merge(entity)).thenReturn(entity);

        abstractBaseDao.remove(entity);

        // then
        verify(entityManager).contains(entity);
        verify(entityManager).remove(entity);
        verify(entityManager).flush();
    }
}
