package com.warehouse.administration.role;

import com.warehouse.enums.UserTypeEnum;

public class UserRoleRequest {

    private String name;
    private String code;
    private UserTypeEnum userType;
    private String searchFor = "";
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

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
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
        return "UserRoleRequest [code=" + code + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", name=" + name + ", searchFor="
                + searchFor + ", userType=" + userType + "]";
    }

}
