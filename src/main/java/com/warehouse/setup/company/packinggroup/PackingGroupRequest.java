package com.warehouse.setup.company.packinggroup;

public class PackingGroupRequest {

    private String name;
    private String code;
    private String searchFor = "";
    private Long primaryCompanyId;
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

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
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
        return "PackingGroupRequest [code=" + code + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", name=" + name
                + ", primaryCompanyId=" + primaryCompanyId + ", searchFor=" + searchFor + "]";
    }

   
}
