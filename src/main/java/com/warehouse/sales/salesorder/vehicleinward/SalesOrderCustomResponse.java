package com.warehouse.sales.salesorder.vehicleinward;

import java.sql.Timestamp;
import java.util.Date;

public class SalesOrderCustomResponse {
    
    private Long id;
    private String soNo;
    private String referenceOrderNo;
    private String fcNo;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private String createdByUserName;
    private String modifiedByUserName;

    public SalesOrderCustomResponse() {

    }

    public SalesOrderCustomResponse(Long id, String soNo, String referenceOrderNo, String fcNo, Date createdAt,
            Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.soNo = soNo;
        this.referenceOrderNo = referenceOrderNo;
        this.fcNo = fcNo;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    public String getFcNo() {
        return fcNo;
    }

    public void setFcNo(String fcNo) {
        this.fcNo = fcNo;
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
}
