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

package com.telefonica.fiware.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telefonica.fiware.commons.openstack.auth.OpenStackAccess;

/**
 * Create and manage the cache to the response of OpenStack for request the admin token. Based on ehcache open source.
 */
public class TokenCache {

    public static final String CACHE_NAME = "token";

    /**
     * The maximum amount of time between accesses before an element expires.
     */
    private static final long TIME_TO_IDLE = 1200L;
    /**
     * The maximum time between creation time and when an element expires.
     */
    private static final long TIME_TO_LIVE = 1200L;

    /**
     * cache.
     */
    private Cache cache;

    /**
     * The log.
     */
    private static Logger log = LoggerFactory.getLogger(TokenCache.class);

    /**
     * Default constructor. Creates the cache.
     */

    public TokenCache() {
        this("/ehcache.xml");
    }

    /**
     * Creates the cache configured in fileName.
     * 
     * @param fileName
     */
    public TokenCache(String fileName) {

        CacheManager singletonManager;
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        try {
            singletonManager = CacheManager.newInstance(inputStream);
            cache = singletonManager.getCache(CACHE_NAME);
            if (cache == null) {
                throw new Exception("Cache " + CACHE_NAME + " does not exist in ehcache file. Caches list:"
                        + Arrays.toString(singletonManager.getCacheNames()));
            }
        } catch (Exception e) {
            log.error("Error reading ehCache file " + e);
            singletonManager = CacheManager.create();
            if (!singletonManager.cacheExists(CACHE_NAME)) {
                singletonManager.addCache(CACHE_NAME);
            }
            cache = singletonManager.getCache(CACHE_NAME);

            CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
            cacheConfiguration.setTimeToIdleSeconds(1200);
            cacheConfiguration.setTimeToLiveSeconds(1200);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("Error closing ehCache file " + e);
                }
            }
        }

    }

    /**
     * Put new a new PaasManagerUser value using token like key, in cache.
     * 
     * @param key
     * @param user
     */
    public void put(String key, Object user) {
        cache.put(new Element(key, user));
    }

    /**
     * Put new admin token in cache.
     * 
     * @param openStackAccess
     */
    public void putAdmin(OpenStackAccess openStackAccess) {
        cache.put(new Element("admin", openStackAccess));
    }

    /**
     * Return admin credentials.
     * 
     * @return
     */
    public OpenStackAccess getAdmin() {
        if (cache.isKeyInCache("admin") && (cache.get("admin") != null)) {
            return (OpenStackAccess) cache.get("admin").getObjectValue();
        } else {
            return null;
        }
    }

    /**
     * Get from cache the PaasManagerUser cached. The key is token string.
     * 
     * @param token
     * @param tenantId
     * @return
     */
    public Object getPaasManagerUser(String token, String tenantId) {
        String key = token + "-" + tenantId;

        if (cache.isKeyInCache(key) && (cache.get(key) != null)) {

            return cache.get(key).getObjectValue();
        } else {
            return null;
        }
    }

    /**
     * Get ehcache.
     * 
     * @return
     */
    public Cache getCache() {
        return cache;
    }
}
