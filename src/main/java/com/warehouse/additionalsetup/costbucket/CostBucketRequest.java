package com.warehouse.additionalsetup.costbucket;

import com.warehouse.enums.CostingMethodEnum;

public class CostBucketRequest {

    private Long id;

    private String code;

    private String name;

    private CostingMethodEnum costingMethod;

    private String mapAllUsers;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String searchFor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CostingMethodEnum getCostingMethod() {
        return costingMethod;
    }

    public void setCostingMethod(CostingMethodEnum costingMethod) {
        this.costingMethod = costingMethod;
    }

    public String getMapAllUsers() {
        return mapAllUsers;
    }

    public void setMapAllUsers(String mapAllUsers) {
        this.mapAllUsers = mapAllUsers;
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
        return "CostBucketRequest [code=" + code + ", costingMethod=" + costingMethod + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", id=" + id + ", mapAllUsers=" + mapAllUsers + ", modifiedFrom="
                + modifiedFrom + ", modifiedTo=" + modifiedTo + ", name=" + name + ", searchFor=" + searchFor + "]";
    }

}
