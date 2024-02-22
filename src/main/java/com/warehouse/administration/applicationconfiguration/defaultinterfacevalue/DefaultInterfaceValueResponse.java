package com.warehouse.administration.applicationconfiguration.defaultinterfacevalue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultInterfaceValueResponse {

    private Long primaryCompanyId;

    private String primaryCompanyName;

    private String primaryCompanyCode;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String createdByUserName;

    private String modifiedByUserName;

    private List<DefaultInterfaceValue> defaultInterfaceValueList = new ArrayList<>();
    
    public DefaultInterfaceValueResponse(Long primaryCompanyId, String primaryCompanyName,
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

    public List<DefaultInterfaceValue> getDefaultInterfaceValueList() {
        return defaultInterfaceValueList;
    }

    public void setDefaultInterfaceValueList(List<DefaultInterfaceValue> defaultInterfaceValueList) {
        this.defaultInterfaceValueList = defaultInterfaceValueList;
    }

    @Override
    public String toString() {
        return "DefaultInterfaceValueResponse [createdAt=" + createdAt + ", createdByUserName=" + createdByUserName
                + ", defaultInterfaceValueList=" + defaultInterfaceValueList + ", modifiedAt=" + modifiedAt
                + ", modifiedByUserName=" + modifiedByUserName + ", primaryCompanyCode=" + primaryCompanyCode
                + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + "]";
    }

}
