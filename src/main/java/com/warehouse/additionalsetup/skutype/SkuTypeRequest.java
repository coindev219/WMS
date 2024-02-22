package com.warehouse.additionalsetup.skutype;

public class SkuTypeRequest {

    private Long id;

    private String code;

    private String name;

    private Integer captureLotSize;

    private Long primaryCompanyId;

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

    public Integer getCaptureLotSize() {
        return captureLotSize;
    }

    public void setCaptureLotSize(Integer captureLotSize) {
        this.captureLotSize = captureLotSize;
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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    @Override
    public String toString() {
        return "SkuTypeRequest [captureLotSize=" + captureLotSize + ", code=" + code + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", id=" + id + ", modifiedFrom=" + modifiedFrom + ", modifiedTo="
                + modifiedTo + ", name=" + name + ", primaryCompanyId=" + primaryCompanyId + ", searchFor=" + searchFor
                + "]";
    }

}
