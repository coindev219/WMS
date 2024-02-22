package com.warehouse.administration.applicationconfiguration.warehouseparameters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseConfigParamtersResponse {

    private Long warehouseId;

    private String warehouseName;

    private String warehouseCode;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String createdByUserName;

    private String modifiedByUserName;

    private List<WarehouseConfig> warehouseConfigList = new ArrayList<>();
    
    public WarehouseConfigParamtersResponse(Long warehouseId, String warehouseName,
            String warehouseCode, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
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

    public List<WarehouseConfig> getWarehouseConfigList() {
        return warehouseConfigList;
    }

    public void setWarehouseConfigList(List<WarehouseConfig> warehouseConfigList) {
        this.warehouseConfigList = warehouseConfigList;
    }

    @Override
    public String toString() {
        return "WarehouseConfigParamtersResponse [createdAt=" + createdAt + ", createdByUserName=" + createdByUserName
                + ", modifiedAt=" + modifiedAt + ", modifiedByUserName=" + modifiedByUserName + ", warehouseCode="
                + warehouseCode + ", warehouseConfigList=" + warehouseConfigList + ", warehouseId=" + warehouseId
                + ", warehouseName=" + warehouseName + "]";
    }

    

    

}
