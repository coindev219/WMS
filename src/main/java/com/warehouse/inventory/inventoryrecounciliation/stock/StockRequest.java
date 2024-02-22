package com.warehouse.inventory.inventoryrecounciliation.stock;

public class StockRequest {

    private Long id;

    private String stockNo;

    private Long primaryCompanyId;

    private Long costBucketId;

    private Long warehouseId;

    private String cumulativeLocationCode;

    private String locationHierarchyID;

    private Long locationsMovedInUnit;

    private Long includeLocationsThatMovedIn;

    private Boolean inventoryReconciliationInstructionName;

    private Boolean isBlindCount;

    private Boolean isExistingStockCountName;

    private Boolean isRecountOnBatchNo;

    private Boolean isRecountOnExpiryDate;

    private Boolean isRecountOnLocation;

    private Boolean isRecountOnManufactureDate;

    private Boolean isRecountOnPackNumber;

    private Boolean isRecountOnSerialNo;

    private Boolean isRecountRequired;

    private Boolean isSelected;

    private Long maxNoOfLinesPerInstruction;

    private Boolean verifyBatchNo;

    private Boolean verifyExpiryDate;

    private Boolean verifyManufactureDate;

    private Boolean verifyPackNumber;

    private Boolean verifySerialNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getCostBucketId() {
        return costBucketId;
    }

    public void setCostBucketId(Long costBucketId) {
        this.costBucketId = costBucketId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCumulativeLocationCode() {
        return cumulativeLocationCode;
    }

    public void setCumulativeLocationCode(String cumulativeLocationCode) {
        this.cumulativeLocationCode = cumulativeLocationCode;
    }

    public String getLocationHierarchyID() {
        return locationHierarchyID;
    }

    public void setLocationHierarchyID(String locationHierarchyID) {
        this.locationHierarchyID = locationHierarchyID;
    }

    public Long getLocationsMovedInUnit() {
        return locationsMovedInUnit;
    }

    public void setLocationsMovedInUnit(Long locationsMovedInUnit) {
        this.locationsMovedInUnit = locationsMovedInUnit;
    }

    public Long getIncludeLocationsThatMovedIn() {
        return includeLocationsThatMovedIn;
    }

    public void setIncludeLocationsThatMovedIn(Long includeLocationsThatMovedIn) {
        this.includeLocationsThatMovedIn = includeLocationsThatMovedIn;
    }

    public Boolean getInventoryReconciliationInstructionName() {
        return inventoryReconciliationInstructionName;
    }

    public void setInventoryReconciliationInstructionName(Boolean inventoryReconciliationInstructionName) {
        this.inventoryReconciliationInstructionName = inventoryReconciliationInstructionName;
    }

    public Boolean getBlindCount() {
        return isBlindCount;
    }

    public void setBlindCount(Boolean blindCount) {
        isBlindCount = blindCount;
    }

    public Boolean getExistingStockCountName() {
        return isExistingStockCountName;
    }

    public void setExistingStockCountName(Boolean existingStockCountName) {
        isExistingStockCountName = existingStockCountName;
    }

    public Boolean getRecountOnBatchNo() {
        return isRecountOnBatchNo;
    }

    public void setRecountOnBatchNo(Boolean recountOnBatchNo) {
        isRecountOnBatchNo = recountOnBatchNo;
    }

    public Boolean getRecountOnExpiryDate() {
        return isRecountOnExpiryDate;
    }

    public void setRecountOnExpiryDate(Boolean recountOnExpiryDate) {
        isRecountOnExpiryDate = recountOnExpiryDate;
    }

    public Boolean getRecountOnLocation() {
        return isRecountOnLocation;
    }

    public void setRecountOnLocation(Boolean recountOnLocation) {
        isRecountOnLocation = recountOnLocation;
    }

    public Boolean getRecountOnManufactureDate() {
        return isRecountOnManufactureDate;
    }

    public void setRecountOnManufactureDate(Boolean recountOnManufactureDate) {
        isRecountOnManufactureDate = recountOnManufactureDate;
    }

    public Boolean getRecountOnPackNumber() {
        return isRecountOnPackNumber;
    }

    public void setRecountOnPackNumber(Boolean recountOnPackNumber) {
        isRecountOnPackNumber = recountOnPackNumber;
    }

    public Boolean getRecountOnSerialNo() {
        return isRecountOnSerialNo;
    }

    public void setRecountOnSerialNo(Boolean recountOnSerialNo) {
        isRecountOnSerialNo = recountOnSerialNo;
    }

    public Boolean getRecountRequired() {
        return isRecountRequired;
    }

    public void setRecountRequired(Boolean recountRequired) {
        isRecountRequired = recountRequired;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Long getMaxNoOfLinesPerInstruction() {
        return maxNoOfLinesPerInstruction;
    }

    public void setMaxNoOfLinesPerInstruction(Long maxNoOfLinesPerInstruction) {
        this.maxNoOfLinesPerInstruction = maxNoOfLinesPerInstruction;
    }

    public Boolean getVerifyBatchNo() {
        return verifyBatchNo;
    }

    public void setVerifyBatchNo(Boolean verifyBatchNo) {
        this.verifyBatchNo = verifyBatchNo;
    }

    public Boolean getVerifyExpiryDate() {
        return verifyExpiryDate;
    }

    public void setVerifyExpiryDate(Boolean verifyExpiryDate) {
        this.verifyExpiryDate = verifyExpiryDate;
    }

    public Boolean getVerifyManufactureDate() {
        return verifyManufactureDate;
    }

    public void setVerifyManufactureDate(Boolean verifyManufactureDate) {
        this.verifyManufactureDate = verifyManufactureDate;
    }

    public Boolean getVerifyPackNumber() {
        return verifyPackNumber;
    }

    public void setVerifyPackNumber(Boolean verifyPackNumber) {
        this.verifyPackNumber = verifyPackNumber;
    }

    public Boolean getVerifySerialNo() {
        return verifySerialNo;
    }

    public void setVerifySerialNo(Boolean verifySerialNo) {
        this.verifySerialNo = verifySerialNo;
    }

    @Override
    public String toString() {
        return "StockRequest{" + "id=" + id + ", stockNo='" + stockNo + '\'' + ", primaryCompanyId=" + primaryCompanyId
                + ", costBucketId=" + costBucketId + ", customerId=" + warehouseId + ", cumulativeLocationCode='"
                + cumulativeLocationCode + '\'' + ", locationHierarchyID='" + locationHierarchyID + '\''
                + ", locationsMovedInUnit=" + locationsMovedInUnit + ", includeLocationsThatMovedIn="
                + includeLocationsThatMovedIn + ", inventoryReconciliationInstructionName="
                + inventoryReconciliationInstructionName + ", isBlindCount=" + isBlindCount
                + ", isExistingStockCountName=" + isExistingStockCountName + ", isRecountOnBatchNo="
                + isRecountOnBatchNo + ", isRecountOnExpiryDate=" + isRecountOnExpiryDate + ", isRecountOnLocation="
                + isRecountOnLocation + ", isRecountOnManufactureDate=" + isRecountOnManufactureDate
                + ", isRecountOnPackNumber=" + isRecountOnPackNumber + ", isRecountOnSerialNo=" + isRecountOnSerialNo
                + ", isRecountRequired=" + isRecountRequired + ", isSelected=" + isSelected
                + ", maxNoOfLinesPerInstruction=" + maxNoOfLinesPerInstruction + ", verifyBatchNo=" + verifyBatchNo
                + ", verifyExpiryDate=" + verifyExpiryDate + ", verifyManufactureDate=" + verifyManufactureDate
                + ", verifyPackNumber=" + verifyPackNumber + ", verifySerialNo=" + verifySerialNo + '}';
    }
}
