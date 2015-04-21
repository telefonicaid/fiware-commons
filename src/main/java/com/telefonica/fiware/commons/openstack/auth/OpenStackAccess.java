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

import net.sf.json.JSONObject;

/**
 * Class to encapsulates information about token.
 */
public class OpenStackAccess {

    private String token;

    private String tenantId;

    private String tenantName;

    private JSONObject accessJSON;

    private String api;

    private OpenStackKeystone openStackKeystone;

    /**
     * Get the token.
     * 
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token.
     * 
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get tenant id.
     * 
     * @return
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Set tenant Id.
     * 
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Get tenant name.
     * 
     * @return
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Set tenant name.
     * 
     * @param tenantName
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Get Json with keystone response.
     * 
     * @return
     */
    public JSONObject getAccessJSON() {
        return accessJSON;
    }

    /**
     * Set json with keystone response.
     * 
     * @param accessJSON
     */
    public void setAccessJSON(JSONObject accessJSON) {
        this.accessJSON = accessJSON;
    }

    /**
     * get API version.
     * 
     * @return
     */
    public String getApi() {
        return api;
    }

    /**
     * Set API version.
     * 
     * @param api
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * get keystone class to process/parse keystone request/response.
     * 
     * @return
     */
    public OpenStackKeystone getOpenStackKeystone() {
        return openStackKeystone;
    }

    /**
     * set keystone class to process/parse keystone request/response.
     * 
     * @param openStackKeystone
     */
    public void setOpenStackKeystone(OpenStackKeystone openStackKeystone) {
        this.openStackKeystone = openStackKeystone;
    }
}
