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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.telefonica.fiware.commons.openstack.auth.OpenStackAccess;

public class TokenCacheTest {

    @Test
    public void shouldPutNewUserInCache() {
        // given

        TokenCache tokenCache = new TokenCache();
        String user = "user1";

        // when
        tokenCache.put("token1-tenantId", user);

        // then
        assertEquals("user1", tokenCache.getPaasManagerUser("token1", "tenantId"));
    }

    @Test
    public void shouldPutNewTokenForAdminInCache() {
        // given
        TokenCache tokenCache = new TokenCache();
        OpenStackAccess openStackAccess = new OpenStackAccess();
        openStackAccess.setToken("token1");

        // when
        tokenCache.putAdmin(openStackAccess);

        // then
        assertEquals("token1", tokenCache.getAdmin().getToken());
    }

    @Test
    public void shouldReturnNullWithEmptyCache() {
        // given
        TokenCache tokenCache = new TokenCache();

        tokenCache.getCache().removeAll();
        // when
        OpenStackAccess openStackAccess = tokenCache.getAdmin();

        // then
        assertNull(openStackAccess);
    }
}
