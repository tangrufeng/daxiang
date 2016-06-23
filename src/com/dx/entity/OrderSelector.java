package com.dx.entity;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Tom on 16/6/21.
 */
@JsonIgnoreProperties
public class OrderSelector extends OrderBean{

    private String supplierId;

    private String page;

    private String pageSize;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
