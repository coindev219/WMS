package com.warehouse.administration.applicationconfiguration.organizationparameters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrganizationConfigParamtersResponse {

    private Long organizationId;

    private String organizationName;

    private String organizationCode;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private List<OrganizationConfig> organizationConfigList = new ArrayList<>();

    public OrganizationConfigParamtersResponse(Long organizationId, String organizationName, String organizationCode,
            Date createdAt, Date modifiedAt) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationCode = organizationCode;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
       
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
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

    public List<OrganizationConfig> getOrganizationConfigList() {
        return organizationConfigList;
    }

    public void setOrganizationConfigList(List<OrganizationConfig> organizationConfigList) {
        this.organizationConfigList = organizationConfigList;
    }

    @Override
    public String toString() {
        return "OrganizationConfigParamtersResponse [createdAt=" + createdAt + ", modifiedAt=" + modifiedAt
                + ", organizationCode=" + organizationCode + ", organizationConfigList=" + organizationConfigList
                + ", organizationId=" + organizationId + ", organizationName=" + organizationName + "]";
    }

    

}
