package com.warehouse.setup.warehouse.integrationmapping;

import java.util.List;

public class IntegrationMappingListResponse {

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String contactName;

    private List<IntegrationMapping> integrationMappingList;

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
   
    public List<IntegrationMapping> getIntegrationMappingList() {
        return integrationMappingList;
    }

    public void setIntegrationMappingList(List<IntegrationMapping> integrationMappingList) {
        this.integrationMappingList = integrationMappingList;
    }

    public IntegrationMappingListResponse(Long warehouseId, String code, String name, String contactName) {
        this.warehouseId = warehouseId;
        this.warehouseCode = code;
        this.warehouseName = name;
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return "IntegrationMappingListResponse [code=" + warehouseCode + ", contactName=" + contactName
                + ", integrationMappingList=" + integrationMappingList + ", name=" + warehouseName + ", warehouseId="
                + warehouseId + "]";
    }

    

}
