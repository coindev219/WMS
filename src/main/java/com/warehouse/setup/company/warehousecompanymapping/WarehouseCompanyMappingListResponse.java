package com.warehouse.setup.company.warehousecompanymapping;

import java.util.List;

public class WarehouseCompanyMappingListResponse {

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String contactName;

    private List<WarehouseCompanyMapping> warehouseCompanyMappingList;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<WarehouseCompanyMapping> getWarehouseCompanyMappingList() {
        return warehouseCompanyMappingList;
    }

    public void setWarehouseCompanyMappingList(List<WarehouseCompanyMapping> warehouseCompanyMappingList) {
        this.warehouseCompanyMappingList = warehouseCompanyMappingList;
    }

    public WarehouseCompanyMappingListResponse(Long warehouseId, String code, String name, String contactName) {
        this.warehouseId = warehouseId;
        this.warehouseCode = code;
        this.warehouseName = name;
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return "WarehouseCompanyMappingListResponse [code=" + warehouseCode + ", contactName=" + contactName + ", name="
                + warehouseName + ", warehouseCompanyMappingList=" + warehouseCompanyMappingList + ", warehouseId="
                + warehouseId + "]";
    }

}
