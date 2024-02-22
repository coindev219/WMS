package com.warehouse.setup.sku.costbucketmapping;

public class SkuCostBucketMappingRequest {

    private Long warehouseId;

    private Long primaryCompanyId;

    private Long skuId;

    private Long costBucketId;

    private Integer bucketQty;

    private Float movingAverage;

    private Float warehousePrimaryCompanyShipTo;

    private Float warehouseShipTo;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String searchFor;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getCostBucketId() {
        return costBucketId;
    }

    public void setCostBucketId(Long costBucketId) {
        this.costBucketId = costBucketId;
    }

    public Integer getBucketQty() {
        return bucketQty;
    }

    public void setBucketQty(Integer bucketQty) {
        this.bucketQty = bucketQty;
    }

    public Float getMovingAverage() {
        return movingAverage;
    }

    public void setMovingAverage(Float movingAverage) {
        this.movingAverage = movingAverage;
    }

    public Float getWarehousePrimaryCompanyShipTo() {
        return warehousePrimaryCompanyShipTo;
    }

    public void setWarehousePrimaryCompanyShipTo(Float warehousePrimaryCompanyShipTo) {
        this.warehousePrimaryCompanyShipTo = warehousePrimaryCompanyShipTo;
    }

    public Float getWarehouseShipTo() {
        return warehouseShipTo;
    }

    public void setWarehouseShipTo(Float warehouseShipTo) {
        this.warehouseShipTo = warehouseShipTo;
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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    @Override
    public String toString() {
        return "SkuCostBucketMappingRequest [bucketQty=" + bucketQty + ", costBucketId=" + costBucketId
                + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", movingAverage=" + movingAverage + ", primaryCompanyId="
                + primaryCompanyId + ", searchFor=" + searchFor + ", skuId=" + skuId + ", warehouseId=" + warehouseId
                + ", warehousePrimaryCompanyShipTo=" + warehousePrimaryCompanyShipTo + ", warehouseShipTo="
                + warehouseShipTo + "]";
    }

}
