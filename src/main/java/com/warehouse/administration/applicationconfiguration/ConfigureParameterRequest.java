package com.warehouse.administration.applicationconfiguration;

public class ConfigureParameterRequest {

    private Long primaryCompanyId;
    private Long warehouseId;
    private String searchFor = "";
    private String createdFrom;
    private String createdTo;
    private String modifiedFrom;
    private String modifiedTo;

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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
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
        return "ConfigureParameterRequest [createdFrom=" + createdFrom + ", createdTo=" + createdTo + ", modifiedFrom="
                + modifiedFrom + ", modifiedTo=" + modifiedTo + ", primaryCompanyId=" + primaryCompanyId
                + ", searchFor=" + searchFor + ", warehouseId=" + warehouseId + "]";
    }

}
