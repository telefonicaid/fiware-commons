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

package com.telefonica.fiware.commons.dao;

/**
 * Provides a set of common fields in search criteria.
 */
public class AbstractSearchCriteria {
    public final static String ASC = "asc";
    public final static String DESC = "desc";

    /** The number of page zero-based. */
    private Integer page;

    /** The number of elements shwon per page. */
    private Integer pageSize;

    /** The field to order the resultset */
    private String orderBy;

    /**
     * asc or desc defines if the result will be ordered ascending or descending.
     */
    private String orderType;

    /**
     * Constructor of the class.
     */
    public AbstractSearchCriteria() {
        this.page = null;
        this.pageSize = null;
        this.orderBy = "id";
        this.orderType = ASC;
    }

    /**
     * Constructor of the class.
     * 
     * @param page
     *            the page number
     * @param pageSize
     *            the page size
     * @param orderBy
     *            the field name to order by
     * @param orderType
     *            the order type
     */
    public AbstractSearchCriteria(Integer page, Integer pageSize, String orderBy, String orderType) {
        this.page = page;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.orderType = orderType;
    }

    /**
     * @param page
     * @param pageSize
     */
    public AbstractSearchCriteria(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * @param orderBy
     * @param orderType
     */
    public AbstractSearchCriteria(String orderBy, String orderType) {
        this.orderBy = orderBy;
        this.orderType = orderType;
    }

    // ////////////// ACCESSORS //////////////
    /**
     * @return the page.
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the pageSize.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the orderBy.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            the orderBy to set
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return the orderType.
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType
     *            the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
