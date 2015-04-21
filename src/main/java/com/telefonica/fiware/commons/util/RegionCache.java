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

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * Cache of url regions. Implementations based on ehcache.
 */
public class RegionCache {

    public static final String CACHE_NAME = "regions";

    private Cache cache;

    /**
     * Creates a cache.
     */
    public RegionCache() {

        CacheManager singletonManager;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/ehcache.xml");
            singletonManager = CacheManager.newInstance(inputStream);
        } catch (Exception e) {
            singletonManager = CacheManager.create();
            singletonManager.addCache(CACHE_NAME);
            cache.getCacheConfiguration();
            cache = singletonManager.getCache(CACHE_NAME);
            CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
            cacheConfiguration.setTimeToIdleSeconds(300);
            cacheConfiguration.setTimeToLiveSeconds(300);
            e.printStackTrace();

        }
        cache = singletonManager.getCache(CACHE_NAME);

    }

    /**
     * Put a new url in cache.
     * 
     * @param region
     * @param service
     * @param url
     */
    public void putUrl(String region, String service, String url) {
        String key = getKey(region, service);
        cache.put(new Element(key, url));
    }

    /**
     * Get an url from cache.
     * 
     * @param region
     * @param service
     * @return
     */
    public String getUrl(String region, String service) {

        String key = getKey(region, service);

        if (cache.isKeyInCache(key) && (cache.get(key) != null)) {

            return (String) cache.get(key).getObjectValue();
        } else {
            return null;
        }
    }

    /**
     * Generate the key with a region and a service.
     * 
     * @param region
     * @param service
     * @return
     */
    private String getKey(String region, String service) {
        return region + "_" + service;
    }

    /**
     * Clean the cache.
     */
    public void clear() {
        cache.removeAll();
    }

    /**
     * Get configuration cache.
     * 
     * @return
     */
    public CacheConfiguration getConfiguration() {
        CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
        return cacheConfiguration;
    }
}
