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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.telefonica.fiware.commons.properties.PropertiesProvider;

/**
 * Implementation of the Interface PropertiesUtils which manages the creation of EntityManagers.
 * <p>
 * Each method invocation is executed as one different transaction.
 */
public class PropertiesProviderTxImpl implements PropertiesProvider {

    private final EntityManagerFactory emf;

    /**
     * Constructor of the class.
     */
    public PropertiesProviderTxImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * @param namespace
     *            The namespace of the properties to be load (also the filename)
     * @return
     */
    public Properties load(final String namespace) {
        EntityManager em = emf.createEntityManager();
        try {
            return createPropertiesProvider(em).load(namespace);
        } finally {
            em.close();
        }
    }

    /**
     * @param prop
     * @param namespace
     *            The namespace of the object
     */
    public void store(final Properties prop, final String namespace) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        boolean isActive = true;
        try {
            tx = em.getTransaction();
            if (!tx.isActive()) {
                isActive = false;
                tx.begin();
            }
            createPropertiesProvider(em).store(prop, namespace);
            if (!isActive) {
                tx.commit();
            }
        } finally {
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getNamespaces() {
        EntityManager em = emf.createEntityManager();
        try {
            return createPropertiesProvider(em).getNamespaces();
        } finally {
            em.close();
        }
    }

    /**
     * Builds default impl of properties util.
     * 
     * @param em
     * @return
     */
    private PropertiesProvider createPropertiesProvider(EntityManager em) {
        return new PropertiesProviderImpl(new PropertiesDAOJPAImpl(em));
    }

}
