package com.warehouse.administration.applicationconfiguration.defaultinterfacevalue;

public class DefaultInterfaceValueSearchRequest {

    private String searchFor = "";
    private String createdFrom;
    private String createdTo;
    private String modifiedFrom;
    private String modifiedTo;
    private String primaryCompanyName;
    private String primaryCompanyCode;

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

    public String getPrimaryCompanyName() {
        return primaryCompanyName;
    }

    public void setPrimaryCompanyName(String primaryCompanyName) {
        this.primaryCompanyName = primaryCompanyName;
    }

    public String getPrimaryCompanyCode() {
        return primaryCompanyCode;
    }

    public void setPrimaryCompanyCode(String primaryCompanyCode) {
        this.primaryCompanyCode = primaryCompanyCode;
    }

    @Override
    public String toString() {
        return "DefaultInterfaceValueSearchRequest [createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", primaryCompanyCode="
                + primaryCompanyCode + ", primaryCompanyName=" + primaryCompanyName + ", searchFor=" + searchFor + "]";
    }

}
