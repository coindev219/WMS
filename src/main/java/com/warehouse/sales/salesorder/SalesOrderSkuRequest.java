package com.warehouse.sales.salesorder;

public class SalesOrderSkuRequest {

    private Long id;
    private Long reasonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    public String toString() {
        return "SalesOrderSkuRequest [id=" + id + ", reasonId=" + reasonId + "]";
    }

}
