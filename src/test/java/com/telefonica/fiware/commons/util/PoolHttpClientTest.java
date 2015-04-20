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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Client;

import org.apache.http.conn.HttpClientConnectionManager;
import org.junit.Test;

public class PoolHttpClientTest {

    @Test
    public void shouldCreatesClient() {
        // given

        HttpClientConnectionManager httpConnectionManager = mock(HttpClientConnectionManager.class);
        // when
        Client client = PoolHttpClient.getInstance(httpConnectionManager).getClient();

        // then
        assertNotNull(client);
    }

    @Test
    public void shouldAssignClient() {
        // given

        HttpClientConnectionManager httpConnectionManager = mock(HttpClientConnectionManager.class);
        PoolHttpClient poolHttpClient = PoolHttpClient.getInstance(httpConnectionManager);
        Client client = mock(Client.class);
        // when
        poolHttpClient.setClient(client);

        // then
        assertEquals(client, poolHttpClient.getClient());
    }
}
