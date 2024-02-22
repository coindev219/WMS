package com.warehouse.setup.sku.warehousemapping;

import java.util.List;

public class SkuWarehouseMappingListResponse {

    private Long warehouseCompanyMappingId;
    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private Long primaryCompanyId;
    private String primaryCompanyCode;
    private String primaryCompanyName;
    private List<SkuWarehouseMapping> skuWarehouseMappingList;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCode() {
        return warehouseCode;
    }

    public void setCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getName() {
        return warehouseName;
    }

    public void setName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public String getPrimaryCompanyCode() {
        return primaryCompanyCode;
    }

    public void setPrimaryCompanyCode(String primaryCompanyCode) {
        this.primaryCompanyCode = primaryCompanyCode;
    }

    public String getPrimaryCompanyName() {
        return primaryCompanyName;
    }

    public void setPrimaryCompanyName(String primaryCompanyName) {
        this.primaryCompanyName = primaryCompanyName;
    }

    public List<SkuWarehouseMapping> getSkuWarehouseMappingList() {
        return skuWarehouseMappingList;
    }

    public void setSkuWarehouseMappingList(List<SkuWarehouseMapping> skuWarehouseMappingList) {
        this.skuWarehouseMappingList = skuWarehouseMappingList;
    }

    public Long getWarehouseCompanyMappingId() {
        return warehouseCompanyMappingId;
    }

    public void setWarehouseCompanyMappingId(Long warehouseCompanyMappingId) {
        this.warehouseCompanyMappingId = warehouseCompanyMappingId;
    }

    public SkuWarehouseMappingListResponse(Long warehouseCompanyMappingId, Long warehouseId, String warehouseCode,
            String warehouseName, Long primaryCompanyId, String primaryCompanyCode, String primaryCompanyName) {
        this.warehouseCompanyMappingId = warehouseCompanyMappingId;
        this.warehouseId = warehouseId;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyName = primaryCompanyName;
    }

    @Override
    public String toString() {
        return "SkuWarehouseMappingListResponse [primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", skuWarehouseMappingList="
                + skuWarehouseMappingList + ", warehouseCode=" + warehouseCode + ", warehouseCompanyMappingId="
                + warehouseCompanyMappingId + ", warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

}
