package com.telefonica.euro_iaas.commons.dao;
/**
 * Provides a set of common fields in search criteria.
 *
 * @author Sergio Arroyo
 *
 */
public class AbstractSearchCriteria {
    public final static String ASC = "asc";
    public final static String DESC = "desc";

    /** The number of page zero-based. */
    private Integer page;

    /** The number of elements shwon per page. */
    private Integer pageSize;

    /** The field to order the resultset*/
    private String orderBy;

    /** asc or desc
     *  defines if the result will be ordered ascending or descending.*/
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
     * @param page the page number
     * @param pageSize the page size
     * @param orderBy the field name to order by
     * @param orderType the order type
     */
    public AbstractSearchCriteria(Integer page, Integer pageSize, String orderBy,
            String orderType) {
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

    //////////////// ACCESSORS //////////////
    /**
     * @return the page.
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
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
     * @param pageSize the pageSize to set
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
     * @param orderBy the orderBy to set
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
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
