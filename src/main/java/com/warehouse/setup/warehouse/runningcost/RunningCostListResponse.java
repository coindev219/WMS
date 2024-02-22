package com.warehouse.setup.warehouse.runningcost;

import java.util.List;

public class RunningCostListResponse {

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String contactName;

    private List<RunningCost> runningCostList;

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

    public List<RunningCost> getRunningCostList() {
        return runningCostList;
    }

    public void setRunningCostList(List<RunningCost> runningCostList) {
        this.runningCostList = runningCostList;
    }

    public RunningCostListResponse(Long warehouseId, String code, String name, String contactName) {
        this.warehouseId = warehouseId;
        this.warehouseCode = code;
        this.warehouseName = name;
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return "RunningCostListResponse [code=" + warehouseCode + ", contactName=" + contactName + ", name=" + warehouseName
                + ", runningCostList=" + runningCostList + ", warehouseId=" + warehouseId + "]";
    }

}
