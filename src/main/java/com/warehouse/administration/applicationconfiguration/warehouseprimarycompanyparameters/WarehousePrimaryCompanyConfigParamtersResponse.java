package com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehousePrimaryCompanyConfigParamtersResponse {

    private Long warehouseId;

    private String warehouseName;

    private String warehouseCode;

    private Long primaryCompanyId;

    private String primaryCompanyName;

    private String primaryCompanyCode;

    private Long warehouseCompanyMappingId;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String createdByUserName;

    private String modifiedByUserName;

    private List<WarehousePrimaryCompanyConfig> warehousePrimaryCompanyConfigList = new ArrayList<>();

    
    public WarehousePrimaryCompanyConfigParamtersResponse() {
    }

    public WarehousePrimaryCompanyConfigParamtersResponse(Long warehouseId, String warehouseName, String warehouseCode,
            Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode, Long warehouseCompanyMappingId,
            Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.warehouseCompanyMappingId = warehouseCompanyMappingId;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public Long getWarehouseCompanyMappingId() {
        return warehouseCompanyMappingId;
    }

    public void setWarehouseCompanyMappingId(Long warehouseCompanyMappingId) {
        this.warehouseCompanyMappingId = warehouseCompanyMappingId;
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

    public List<WarehousePrimaryCompanyConfig> getWarehousePrimaryCompanyConfigList() {
        return warehousePrimaryCompanyConfigList;
    }

    public void setWarehousePrimaryCompanyConfigList(
            List<WarehousePrimaryCompanyConfig> warehousePrimaryCompanyConfigList) {
        this.warehousePrimaryCompanyConfigList = warehousePrimaryCompanyConfigList;
    }

    @Override
    public String toString() {
        return "WarehousePrimaryCompanyConfigParamtersResponse [createdAt=" + createdAt + ", createdByUserName="
                + createdByUserName + ", modifiedAt=" + modifiedAt + ", modifiedByUserName=" + modifiedByUserName
                + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId
                + ", primaryCompanyName=" + primaryCompanyName + ", warehouseCode=" + warehouseCode
                + ", warehouseCompanyMappingId=" + warehouseCompanyMappingId + ", warehouseId=" + warehouseId
                + ", warehouseName=" + warehouseName + ", warehousePrimaryCompanyConfigList="
                + warehousePrimaryCompanyConfigList + "]";
    }

    

}
