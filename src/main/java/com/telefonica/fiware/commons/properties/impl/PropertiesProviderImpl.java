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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import com.telefonica.fiware.commons.properties.PropertiesProvider;

/**
 * Implementation of the Interface PropertiesUtils.
 */
public class PropertiesProviderImpl implements PropertiesProvider {

    private static final Logger LOGGER = Logger.getLogger(PropertiesProviderImpl.class.getName());

    private static final String CACHE_NAME = "properties";

    private final PropertiesDAO propertiesDAO;

    private Cache cache;

    /**
     * Constructor of the class.
     * 
     * @param dao
     *            to persist and load properties
     */
    public PropertiesProviderImpl(final PropertiesDAO dao) {
        this.propertiesDAO = dao;
        configureCache();

    }

    private void configureCache() {

        CacheManager singletonManager;
        InputStream inputStream = this.getClass().getResourceAsStream("/ehcache.xml");
        try {
            singletonManager = CacheManager.newInstance(inputStream);
            cache = singletonManager.getCache(CACHE_NAME);
            if (cache == null) {
                throw new Exception("Cache " + CACHE_NAME + " does not exist in ehcache file. Caches list:"
                        + Arrays.toString(singletonManager.getCacheNames()));
            }
        } catch (Exception e) {
            LOGGER.severe("Error reading ehCache file " + e);
            singletonManager = CacheManager.create();
            if (!singletonManager.cacheExists(CACHE_NAME)) {
                singletonManager.addCache(CACHE_NAME);
            }
            cache = singletonManager.getCache(CACHE_NAME);
            CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
            cacheConfiguration.setTimeToIdleSeconds(300);
            cacheConfiguration.setTimeToLiveSeconds(300);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.severe("Error closing ehCache file " + e);
                }
            }
        }

    }

    /**
     * @see PropertiesProvider#load(String)
     */
    public Properties load(final String namespace) {

        Element propCached = cache.get(namespace);
        if (propCached == null) {

            Properties prop = loadFromFilesystem(namespace, this);
            Properties modifiedProp = propertiesDAO.load(namespace);
            for (Iterator i = modifiedProp.keySet().iterator(); i.hasNext();) {
                String key = (String) i.next();
                String value = modifiedProp.getProperty(key);
                prop.setProperty(key, value);
            }
            cache.put(new Element(namespace, prop));
            return prop;
        } else {
            return (Properties) propCached.getValue();
        }
    }

    /**
     * Loads a properties file using namespace as file name. If the file is not found returns an empty properties file.
     * 
     * @param namespace
     *            The namespace
     * @param referenceClassLoader
     *            Object to obtain the classloader where the file can be located
     * @return Properties read from the file
     */
    protected Properties loadFromFilesystem(final String namespace, final Object referenceClassLoader) {
        Properties prop = new Properties();
        try {
            InputStream is = referenceClassLoader.getClass().getResourceAsStream(namespace);
            if (is != null) {
                prop.load(is);
            }
        } catch (IOException e) {

            // file not found, return empty properties file.
            LOGGER.info("Namespace " + namespace + " not found.");
        }
        return prop;
    }

    /**
     * @see PropertiesProvider#store(java.util.Properties, String)
     */
    public void store(final Properties prop, final String namespace) {
        propertiesDAO.store(prop, namespace);
        cache.put(new Element(namespace, prop));

    }

    /**
     * {@inheritDoc}
     */
    public List<String> getNamespaces() {
        return propertiesDAO.findNamespaces();
    }

}
