package com.warehouse.setup.location.hierarchy;

import com.warehouse.enums.DataTypeEnum;

public class LocationHierarchyRequest {

    private Long id;
    private String name;
    private String code;
    private Integer noOfDigits;
    private String prefix;
    private String suffix;
    private String usable;
    private DataTypeEnum dataType;
    private String searchFor;
    private Long locationTypeId;
    private Long warehouseId;
    private Long upperHierarchyId;
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

    public Integer getNoOfDigits() {
        return noOfDigits;
    }

    public void setNoOfDigits(Integer noOfDigits) {
        this.noOfDigits = noOfDigits;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String isUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getUsable() {
        return usable;
    }

    public Long getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getUpperHierarchyId() {
        return upperHierarchyId;
    }

    public void setUpperHierarchyId(Long upperHierarchyId) {
        this.upperHierarchyId = upperHierarchyId;
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
        return "LocationHierarchyRequest [code=" + code + ", dataType=" + dataType + ", id=" + id + ", locationTypeId="
                + locationTypeId + ", name=" + name + ", noOfDigits=" + noOfDigits + ", prefix=" + prefix
                + ", searchFor=" + searchFor + ", suffix=" + suffix + ", upperHierarchyId=" + upperHierarchyId
                + ", usable=" + usable + ", warehouseId=" + warehouseId + "]";
    }

}
