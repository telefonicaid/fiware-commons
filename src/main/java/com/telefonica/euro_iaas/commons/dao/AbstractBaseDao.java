/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.dao;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * Base DAO class.
 */
public abstract class AbstractBaseDao<T, ID extends Serializable> implements BaseDAO<T, ID> {

    private static final String FROM_CLAUSE = "from ";

    private static final String QUERY_LOAD_BY_UNIQUE_FIELD = "SELECT o FROM {0} o WHERE o.{1} = :{1}";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public AbstractBaseDao() {
    }

    /**
     * Creates an instance of this component.
     * 
     * @param entityManager
     *            jpa entity manager
     */
    public AbstractBaseDao(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    public T create(T entity) throws InvalidEntityException, AlreadyExistsEntityException {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (EntityExistsException e) {
            throw new AlreadyExistsEntityException(entity, e);
        } catch (PersistenceException e) {
            throw new DaoRuntimeException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            // to attach object this session
            entityManager.remove(entityManager.merge(entity));
        }
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     */
    public T update(T entity) throws InvalidEntityException {
        try {
            entityManager.merge(entity);
            entityManager.flush();
            return entity;
        } catch (PersistenceException e) {
            throw new DaoRuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Returns all the instances of the given entity stored in the repository.
     * 
     * @param clazz
     *            The class of the entity
     * @return A list of all the entities of the given class or an empty list if none is found
     */
    @SuppressWarnings("unchecked")
    protected List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery(FROM_CLAUSE + clazz.getName()).getResultList();
    }

    /**
     * Loads an entity using any of its unique fields.
     * <p>
     * This method will only work with simple unique field. If the unique field is a compound one an exception will be
     * launched.
     * </p>
     * 
     * @param clazz
     *            The class of the entity
     * @param fieldName
     *            The name of the unique field
     * @param fieldValue
     *            The value of the unique field
     * @return The entity identified by the given unique field
     * @throws EntityNotFoundException
     *             If no entity is found identified by the given business key
     */
    @SuppressWarnings("unchecked")
    protected T loadByField(Class<T> clazz, String fieldName, Object fieldValue) throws EntityNotFoundException {

        // Parameter substitution
        String query = MessageFormat.format(QUERY_LOAD_BY_UNIQUE_FIELD, new Object[] { clazz.getName(), fieldName });
        try {
            return (T) getEntityManager().createQuery(query).setParameter(fieldName, fieldValue).getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException(clazz, fieldName, fieldValue);
        }
    }

    /**
     * To obtain the EntityManager that this class wraps.
     * 
     * @return The <code>EntityManager</code>
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Set the EntityManager. Used in <em>setter injection</em>.
     * 
     * @param entityManager
     *            the <code>entityManager</code> to inject.
     */
    protected void getEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // /////// SOME PAGINATION METHODS

    /**
     * set pagination.
     * 
     * @param searchCriteria
     *            the search criteria (page number, page size, order, etc.)
     * @param baseCriteria
     *            criteria
     * @return the criteria that paginates the result.
     */
    protected Criteria setPagination(AbstractSearchCriteria searchCriteria, Criteria baseCriteria) {
        return setPagination(searchCriteria, baseCriteria, false);
    }

    /**
     * set pagination.
     * 
     * @param searchCriteria
     *            the search criteria (page number, page size, order, etc.)
     * @param baseCriteria
     *            criteria
     * @param inverseOrder
     *            defines if the result shall show in inverse order (if default is ASC then will use DESC and
     *            viceversa).
     * @return the criteria that paginates the result.
     */
    protected Criteria setPagination(AbstractSearchCriteria searchCriteria, Criteria baseCriteria, Boolean inverseOrder) {
        checkPaginationParameters(searchCriteria.getPage(), searchCriteria.getPageSize());
        Order order;
        if (!inverseOrder) {
            order = getOrder(searchCriteria.getOrderBy(), searchCriteria.getOrderType());
        } else {
            order = getInverseOrder(searchCriteria.getOrderBy(), searchCriteria.getOrderType());
        }
        baseCriteria.setFirstResult(searchCriteria.getPageSize() * searchCriteria.getPage());
        baseCriteria.setMaxResults(searchCriteria.getPageSize());
        baseCriteria.addOrder(order);
        return baseCriteria;
    }

    /**
     * Check the pagination parameters.
     * 
     * @param page
     *            the page
     * @param pageSize
     *            the page size
     */
    protected void checkPaginationParameters(Integer page, Integer pageSize) {
        if (page < 0) {
            throw new IllegalArgumentException("page should be >= 0");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize should be >= 1");
        }
    }

    /**
     * set pagination if page number and page size are not null.
     * 
     * @param searchCriteria
     *            the search criteria (page number, page size, order, etc.)
     * @param baseCriteria
     *            criteria
     * @return the criteria that paginates the result.
     */
    protected Criteria setOptionalPagination(AbstractSearchCriteria searchCriteria, Criteria baseCriteria) {
        return setOptionalPagination(searchCriteria, baseCriteria, false);
    }

    /**
     * set pagination if page number and page size are not null.
     * 
     * @param searchCriteria
     *            the search criteria (page number, page size, order, etc.)
     * @param baseCriteria
     *            criteria
     * @param inverseOrder
     *            defines if the result shall show in inverse order (if default is ASC then will use DESC and
     *            viceversa).
     * @return the criteria that optionally paginates the result.
     */
    protected Criteria setOptionalPagination(AbstractSearchCriteria searchCriteria, Criteria baseCriteria,
            Boolean inverseOrder) {
        checkNullablePaginationParameters(searchCriteria.getPage(), searchCriteria.getPageSize());
        Order order;
        if (!inverseOrder) {
            order = getOrder(searchCriteria.getOrderBy(), searchCriteria.getOrderType());
        } else {
            order = getInverseOrder(searchCriteria.getOrderBy(), searchCriteria.getOrderType());
        }
        baseCriteria.addOrder(order);
        if (searchCriteria.getPage() != null && searchCriteria.getPageSize() != null) {
            baseCriteria.setFirstResult(searchCriteria.getPageSize() * searchCriteria.getPage());
            baseCriteria.setMaxResults(searchCriteria.getPageSize());
        }
        return baseCriteria;
    }

    /**
     * Check the pagination parameters. This parameters can be null.
     * 
     * @param page
     *            the page
     * @param pageSize
     *            the page size
     */
    protected void checkNullablePaginationParameters(Integer page, Integer pageSize) {
        if (page != null && page < 0) {
            throw new IllegalArgumentException("page should be >= 0");
        }
        if (pageSize != null && pageSize < 1) {
            throw new IllegalArgumentException("pageSize should be >= 1");
        }
    }

    /**
     * Get the order of a given field name and type.
     * 
     * @param orderBy
     *            the field to order the result
     * @param orderType
     *            the type (Asc or Desc).
     * @return the order.
     */
    private Order getOrder(String orderBy, String orderType) {
        if (orderBy == null || "".equals(orderBy)) {
            throw new IllegalArgumentException("orderBy can not be empty or null");
        }
        Order order = Order.desc(orderBy);
        if (orderType == null || orderType.equalsIgnoreCase(AbstractSearchCriteria.ASC)) {
            order = Order.asc(orderBy);
        }
        return order;
    }

    /**
     * Get the order of a given field name and type.
     * 
     * @param orderBy
     *            the field to order the result
     * @param orderType
     *            the type (Asc or Desc).
     * @return the order.
     */
    private Order getInverseOrder(String orderBy, String orderType) {
        if (orderBy == null || "".equals(orderBy)) {
            throw new IllegalArgumentException("orderBy can not be empty or null");
        }
        Order order = Order.asc(orderBy);
        if (orderType == null || orderType.equalsIgnoreCase(AbstractSearchCriteria.ASC)) {
            order = Order.desc(orderBy);
        }
        return order;
    }

}
