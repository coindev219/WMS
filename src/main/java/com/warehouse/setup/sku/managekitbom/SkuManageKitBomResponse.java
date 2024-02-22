package com.warehouse.setup.sku.managekitbom;

import java.util.List;

public class SkuManageKitBomResponse {

    private Long primaryCompanyId;
    private String primaryCompanyCode;
    private String primaryCompanyName;
    private Long kitSkuId;
    private String kitSkuCode;
    private String kitSkuName;
    private Float purchaseUnitPrice;
    private Float salesUnitPrice;
    private Integer noOfComponents;
    private List<SkuManageKitBom> skuManageKitBomList;

    public SkuManageKitBomResponse() {
    }

    public SkuManageKitBomResponse(Long primaryCompanyId, String primaryCompanyCode, String primaryCompanyName,
            Long kitSkuId, String kitSkuCode, String kitSkuName, Float purchaseUnitPrice, Float salesUnitPrice,
            Integer noOfComponents) {
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyName = primaryCompanyName;
        this.kitSkuId = kitSkuId;
        this.kitSkuCode = kitSkuCode;
        this.kitSkuName = kitSkuName;
        this.purchaseUnitPrice = purchaseUnitPrice;
        this.salesUnitPrice = salesUnitPrice;
        this.noOfComponents = noOfComponents;
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

    public Long getKitSkuId() {
        return kitSkuId;
    }

    public void setKitSkuId(Long kitSkuId) {
        this.kitSkuId = kitSkuId;
    }

    public String getKitSkuCode() {
        return kitSkuCode;
    }

    public void setKitSkuCode(String kitSkuCode) {
        this.kitSkuCode = kitSkuCode;
    }

    public String getKitSkuName() {
        return kitSkuName;
    }

    public void setKitSkuName(String kitSkuName) {
        this.kitSkuName = kitSkuName;
    }

    public Float getPurchaseUnitPrice() {
        return purchaseUnitPrice;
    }

    public void setPurchaseUnitPrice(Float purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
    }

    public Float getSalesUnitPrice() {
        return salesUnitPrice;
    }

    public void setSalesUnitPrice(Float salesUnitPrice) {
        this.salesUnitPrice = salesUnitPrice;
    }

    public Integer getNoOfComponents() {
        return noOfComponents;
    }

    public void setNoOfComponents(Integer noOfComponents) {
        this.noOfComponents = noOfComponents;
    }

    public List<SkuManageKitBom> getSkuManageKitBomList() {
        return skuManageKitBomList;
    }

    public void setSkuManageKitBomList(List<SkuManageKitBom> skuManageKitBomList) {
        this.skuManageKitBomList = skuManageKitBomList;
    }

    @Override
    public String toString() {
        return "SkuManageKitBomResponse [kitSkuCode=" + kitSkuCode + ", kitSkuId=" + kitSkuId + ", kitSkuName="
                + kitSkuName + ", noOfComponents=" + noOfComponents + ", primaryCompanyCode=" + primaryCompanyCode
                + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName
                + ", purchaseUnitPrice=" + purchaseUnitPrice + ", salesUnitPrice=" + salesUnitPrice
                + ", skuManageKitBomList=" + skuManageKitBomList + "]";
    }

}
