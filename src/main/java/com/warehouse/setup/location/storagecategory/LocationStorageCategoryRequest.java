package com.warehouse.setup.location.storagecategory;

public class LocationStorageCategoryRequest {

    private String name;
    private String code;
    private String isActive;
    private String searchFor = "";
    private Long warehouseId;
    private String createdFrom;
    private String createdTo;
    private String modifiedFrom;
    private String modifiedTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
        return "LocationStorageCategoryRequest [code=" + code + ", createdFrom=" + createdFrom + ", createdTo="
                + createdTo + ", isActive=" + isActive + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo
                + ", name=" + name + ", searchFor=" + searchFor + ", warehouseId=" + warehouseId + "]";
    }

}
