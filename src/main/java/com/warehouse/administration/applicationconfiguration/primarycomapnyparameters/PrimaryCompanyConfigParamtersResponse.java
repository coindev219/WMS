package com.warehouse.administration.applicationconfiguration.primarycomapnyparameters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrimaryCompanyConfigParamtersResponse {

    private Long primaryCompanyId;

    private String primaryCompanyName;

    private String primaryCompanyCode;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String createdByUserName;

    private String modifiedByUserName;

    private List<PrimaryCompanyConfig> primaryCompanyConfigList = new ArrayList<>();
    
    public PrimaryCompanyConfigParamtersResponse(Long primaryCompanyId, String primaryCompanyName,
            String primaryCompanyCode, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
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

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getModifiedByUserName() {
        return modifiedByUserName;
    }

    public void setModifiedByUserName(String modifiedByUserName) {
        this.modifiedByUserName = modifiedByUserName;
    }

    public List<PrimaryCompanyConfig> getPrimaryCompanyConfigList() {
        return primaryCompanyConfigList;
    }

    public void setPrimaryCompanyConfigList(List<PrimaryCompanyConfig> primaryCompanyConfigList) {
        this.primaryCompanyConfigList = primaryCompanyConfigList;
    }

    @Override
    public String toString() {
        return "PrimaryCompanyConfigParamtersResponse [createdAt=" + createdAt + ", createdByUserName="
                + createdByUserName + ", modifiedAt=" + modifiedAt + ", modifiedByUserName=" + modifiedByUserName
                + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyConfigList="
                + primaryCompanyConfigList + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName="
                + primaryCompanyName + "]";
    }

    

}
