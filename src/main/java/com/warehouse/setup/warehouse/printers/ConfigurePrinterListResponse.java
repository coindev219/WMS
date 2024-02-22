package com.warehouse.setup.warehouse.printers;

import java.util.List;

public class ConfigurePrinterListResponse {

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String contactName;

    private List<ConfigurePrinter> configurePrinterList;

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

    public List<ConfigurePrinter> getConfigurePrinterList() {
        return configurePrinterList;
    }

    public void setConfigurePrinterList(List<ConfigurePrinter> configurePrinterList) {
        this.configurePrinterList = configurePrinterList;
    }

    public ConfigurePrinterListResponse(Long warehouseId, String code, String name, String contactName) {
        this.warehouseId = warehouseId;
        this.warehouseCode = code;
        this.warehouseName = name;
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return "ConfigurePrinterListResponse [code=" + warehouseCode + ", configurePrinterList=" + configurePrinterList
                + ", contactName=" + contactName + ", name=" + warehouseName + ", warehouseId=" + warehouseId + "]";
    }

}
