package com.warehouse.setup.company.warehousecompanymapping;

public class WarehouseCompanyMappingRequest {

    private Long id;

    private Long warehouseId;

    private Long primaryCompanyId;

    private String shipTo;

    private String billTo;

    private String taxInvoicesLabel;

    private String taxInvoicesNo;

    private Float sgst;

    private Float cgst;

    private String isMappingActive;

    private String searchFor;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getTaxInvoicesLabel() {
        return taxInvoicesLabel;
    }

    public void setTaxInvoicesLabel(String taxInvoicesLabel) {
        this.taxInvoicesLabel = taxInvoicesLabel;
    }

    public String getTaxInvoicesNo() {
        return taxInvoicesNo;
    }

    public void setTaxInvoicesNo(String taxInvoicesNo) {
        this.taxInvoicesNo = taxInvoicesNo;
    }

    public Float getSgst() {
        return sgst;
    }

    public void setSgst(Float sgst) {
        this.sgst = sgst;
    }

    public Float getCgst() {
        return cgst;
    }

    public void setCgst(Float cgst) {
        this.cgst = cgst;
    }

    public String getIsMappingActive() {
        return isMappingActive;
    }

    public void setIsMappingActive(String isMappingActive) {
        this.isMappingActive = isMappingActive;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getModifiedFrom() {
        return modifiedFrom;
    }

    public void setModifiedFrom(String modifiedFrom) {
        this.modifiedFrom = modifiedFrom;
    }

    public String getModifiedTo() {
        return modifiedTo;
    }

    public void setModifiedTo(String modifiedTo) {
        this.modifiedTo = modifiedTo;
    }

    @Override
    public String toString() {
        return "WarehouseCompanyMappingRequest [billTo=" + billTo + ", cgst=" + cgst + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", id=" + id + ", isMappingActive=" + isMappingActive + ", modifiedFrom="
                + modifiedFrom + ", modifiedTo=" + modifiedTo + ", primaryCompanyId=" + primaryCompanyId
                + ", searchFor=" + searchFor + ", sgst=" + sgst + ", shipTo=" + shipTo + ", taxInvoicesLabel="
                + taxInvoicesLabel + ", taxInvoicesNo=" + taxInvoicesNo + ", warehouseId=" + warehouseId + "]";
    }

    
    

}
