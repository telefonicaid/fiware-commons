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

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.telefonica.fiware.commons.properties.bean.PersistentProperty;

/**
 * JPA DAO implementation of PropertiesDAO.
 */

@Transactional(propagation = Propagation.REQUIRED)
public class PropertiesDAOJPAImpl implements PropertiesDAO {

    private static final String NAMESPACE = "namespace";

    private static final String FIND_BY_NAMESPACE = "select p from PersistentProperty p where p.namespace = :namespace";

    private static final String FIND_NAMESPACES = "select distinct(namespace) from PersistentProperty";

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Default constructor of the class.
     */
    public PropertiesDAOJPAImpl() {
    }

    /**
     * Constructor of the class.
     * 
     * @param entityManager
     *            The entity manager to use
     */
    public PropertiesDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @see PropertiesDAO#load(String)
     */
    public Properties load(String namespace) {
        Properties prop = new Properties();
        Query query = entityManager.createQuery(FIND_BY_NAMESPACE);
        query.setParameter(NAMESPACE, namespace);
        for (Iterator i = query.getResultList().iterator(); i.hasNext();) {
            PersistentProperty p = (PersistentProperty) i.next();
            prop.setProperty(p.getKey(), p.getValue());
        }
        return prop;
    }

    /**
     * @see PropertiesDAO#store(java.util.Properties, String)
     */
    public void store(Properties properties, String namespace) {
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = properties.getProperty(key);
            PersistentProperty propEntity = new PersistentProperty();
            propEntity.setNamespace(namespace);
            propEntity.setKey(key);
            propEntity.setValue(value);
            entityManager.merge(propEntity);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findNamespaces() {
        Query query = entityManager.createQuery(FIND_NAMESPACES);
        return query.getResultList();
    }

}
