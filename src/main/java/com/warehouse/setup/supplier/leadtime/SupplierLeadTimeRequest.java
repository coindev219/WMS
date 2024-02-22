package com.warehouse.setup.supplier.leadtime;

import java.sql.Timestamp;



public class SupplierLeadTimeRequest {

    private Long id;

    private Long primaryCompanyId;

    private Long warehouseId;

    private Long supplierId;

    private Long skuTypeId;

    private Integer supplierLeadTimeDays;

    private Integer orderCycle;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String searchFor;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getSupplierLeadTimeDays() {
        return supplierLeadTimeDays;
    }

    public void setSupplierLeadTimeDays(Integer supplierLeadTimeDays) {
        this.supplierLeadTimeDays = supplierLeadTimeDays;
    }

    public Integer getOrderCycle() {
        return orderCycle;
    }

    public void setOrderCycle(Integer orderCycle) {
        this.orderCycle = orderCycle;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public Long getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getModifiedFrom() {
        return modifiedFrom;
    }

    public void setModifiedFrom(String modifiedFrom) {
        this.modifiedFrom = modifiedFrom;
    }

    public String getModifiedTo() {
        return modifiedTo;
    }

    public void setModifiedTo(String modifiedTo) {
        this.modifiedTo = modifiedTo;
    }

    @Override
    public String toString() {
        return "SupplierLeadTimeRequest [createdAt=" + createdAt + ", createdFrom=" + createdFrom + ", createdTo="
                + createdTo + ", id=" + id + ", modifiedAt=" + modifiedAt + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", orderCycle=" + orderCycle + ", primaryCompanyId=" + primaryCompanyId
                + ", searchFor=" + searchFor + ", skuTypeId=" + skuTypeId + ", supplierId=" + supplierId
                + ", supplierLeadTimeDays=" + supplierLeadTimeDays + ", warehouseId=" + warehouseId + "]";
    }
}
