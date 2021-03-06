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

package com.telefonica.fiware.commons.openstack.auth;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telefonica.fiware.commons.openstack.auth.exception.AuthenticationConnectionException;

/**
 * Class to obtain a valid token from the OpenStack.
 */
public class OpenStackAuthenticationToken {

    /**
     * HTTP 200 ok.
     */
    public static final int CODE_200 = 200;

    /**
     * HTTP 201 ok.
     */
    public static final int CODE_201 = 201;

    /**
     * The url of the keystone service.
     */
    private String url;
    /**
     * The tenant name.
     */
    private String tenant;
    /**
     * The user of the keystone admin.
     */
    private String user;
    /**
     * The pass of the keystone admin.
     */
    private String pass;

    /**
     * instance of version v2 or v3.
     */
    private OpenStackKeystone openStackKeystone;

    /**
     * The log.
     */
    private static Logger log = LoggerFactory.getLogger(OpenStackAuthenticationToken.class);

    /**
     * The default constructor of the class OpenStackAuthenticationToken.
     * 
     * @param url
     * @param user
     * @param pass
     * @param tenant
     */
    public OpenStackAuthenticationToken(String url, String user, String pass, String tenant) {

        this.url = url;
        this.user = user;
        this.pass = pass;
        this.tenant = tenant;

        boolean containsV3 = url.indexOf(OpenStackKeystoneV3.VERSION) != -1;
        if (containsV3) {
            openStackKeystone = new OpenStackKeystoneV3();
        } else {
            openStackKeystone = new OpenStackKeystoneV2();

        }
    }

    /**
     * Request to OpenStack a valid token/tenantId.
     * 
     * @return The new credential (tenant id and token).
     */
    public OpenStackAccess getAdminCredentials(Client client) {

        OpenStackAccess openStackAccess = new OpenStackAccess();

        log.info("using keystone version: " + openStackKeystone.getVersion());
        log.info("generate new valid token for admin");

        Response response = null;
        try {

            WebTarget wr = client.target(openStackKeystone.getKeystoneURL(url));

            String payload = openStackKeystone.getPayload(user, pass, tenant);

            Invocation.Builder builder = wr.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

            response = builder.post(Entity.entity(payload, MediaType.APPLICATION_JSON));

            int status = response.getStatus();
            if ((status == CODE_200) || (status == CODE_201)) {

                JSONObject jsonObjectResponse = JSONObject.fromObject(response.readEntity(String.class));

                openStackKeystone.parseResponse(openStackAccess, response, jsonObjectResponse);
            } else {
                String exceptionMessage = "Failed : HTTP error code : (" + url + ")" + response.getStatus()
                        + " message: " + response;
                log.error(exceptionMessage);
                throw new AuthenticationConnectionException(exceptionMessage);

            }
        } catch (Exception ex) {
            throw new AuthenticationConnectionException("Error in authentication: " + ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return openStackAccess;

    }

    /**
     * Get keystone url.
     * 
     * @return
     */
    public String getKeystoneURL() {
        return openStackKeystone.getKeystoneURL(url);
    }

}
