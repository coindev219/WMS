package com.warehouse.setup.sku.uommapping;

import java.util.List;

import com.warehouse.enums.ComplexityEnum;

public class SkuUomMappingListResponse {

    private Long primaryCompanyId;
    private String primaryCompanyCode;
    private String primaryCompanyName;
    private Long skuId;
    private String skuCode;
    private String skuName;
    private String additionalStorageUomName = "";
    private String defaultSalesBillingUomName;
    private String defaultPurchaseUomName;
    private String defaultPurchaseBillingUomName;
    private String defaultSalesUomName;
    private String storageUomName;
    private Float storageUomNetWeight;
    private ComplexityEnum storageUomInComplexity;
    private ComplexityEnum storageUomOutComplexity;
    private String storageUomMheName;
    private Float storageUomLenght;
    private Float storageUomHeight;
    private Float storageUomWidth;
    private Float storageUomGrossWeight;
    private List<SkuUomMapping> skuUomMappingList;

    public SkuUomMappingListResponse() {
    }

    public SkuUomMappingListResponse(Long primaryCompanyId, String primaryCompanyCode, String primaryCompanyName,
            Long skuId, String skuCode, String skuName, 
            String defaultSalesBillingUomName, String defaultPurchaseUomName, String defaultPurchaseBillingUomName,
            String defaultSalesUomName, String storageUomName, Float storageUomNetWeight,
            ComplexityEnum storageUomInComplexity, ComplexityEnum storageUomOutComplexity, String storageUomMheName,
            Float storageUomLenght, Float storageUomHeight, Float storageUomWidth, Float storageUomGrossWeight) {
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyName = primaryCompanyName;
        this.skuId = skuId;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.defaultSalesBillingUomName = defaultSalesBillingUomName;
        this.defaultPurchaseUomName = defaultPurchaseUomName;
        this.defaultPurchaseBillingUomName = defaultPurchaseBillingUomName;
        this.defaultSalesUomName = defaultSalesUomName;
        this.storageUomName = storageUomName;
        this.storageUomNetWeight = storageUomNetWeight;
        this.storageUomInComplexity = storageUomInComplexity;
        this.storageUomOutComplexity = storageUomOutComplexity;
        this.storageUomMheName = storageUomMheName;
        this.storageUomLenght = storageUomLenght;
        this.storageUomHeight = storageUomHeight;
        this.storageUomWidth = storageUomWidth;
        this.storageUomGrossWeight = storageUomGrossWeight;
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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getAdditionalStorageUomName() {
        return additionalStorageUomName;
    }

    public void setAdditionalStorageUomName(String additionalStorageUomName) {
        this.additionalStorageUomName = additionalStorageUomName;
    }

    public String getDefaultSalesBillingUomName() {
        return defaultSalesBillingUomName;
    }

    public void setDefaultSalesBillingUomName(String defaultSalesBillingUomName) {
        this.defaultSalesBillingUomName = defaultSalesBillingUomName;
    }

    public String getDefaultPurchaseUomName() {
        return defaultPurchaseUomName;
    }

    public void setDefaultPurchaseUomName(String defaultPurchaseUomName) {
        this.defaultPurchaseUomName = defaultPurchaseUomName;
    }

    public String getDefaultPurchaseBillingUomName() {
        return defaultPurchaseBillingUomName;
    }

    public void setDefaultPurchaseBillingUomName(String defaultPurchaseBillingUomName) {
        this.defaultPurchaseBillingUomName = defaultPurchaseBillingUomName;
    }

    public String getDefaultSalesUomName() {
        return defaultSalesUomName;
    }

    public void setDefaultSalesUomName(String defaultSalesUomName) {
        this.defaultSalesUomName = defaultSalesUomName;
    }

    public String getStorageUomName() {
        return storageUomName;
    }

    public void setStorageUomName(String storageUomName) {
        this.storageUomName = storageUomName;
    }

    public Float getStorageUomNetWeight() {
        return storageUomNetWeight;
    }

    public void setStorageUomNetWeight(Float storageUomNetWeight) {
        this.storageUomNetWeight = storageUomNetWeight;
    }

    public ComplexityEnum getStorageUomInComplexity() {
        return storageUomInComplexity;
    }

    public void setStorageUomInComplexity(ComplexityEnum storageUomInComplexity) {
        this.storageUomInComplexity = storageUomInComplexity;
    }

    public ComplexityEnum getStorageUomOutComplexity() {
        return storageUomOutComplexity;
    }

    public void setStorageUomOutComplexity(ComplexityEnum storageUomOutComplexity) {
        this.storageUomOutComplexity = storageUomOutComplexity;
    }

    public String getStorageUomMheName() {
        return storageUomMheName;
    }

    public void setStorageUomMheName(String storageUomMheName) {
        this.storageUomMheName = storageUomMheName;
    }

    public Float getStorageUomLenght() {
        return storageUomLenght;
    }

    public void setStorageUomLenght(Float storageUomLenght) {
        this.storageUomLenght = storageUomLenght;
    }

    public Float getStorageUomHeight() {
        return storageUomHeight;
    }

    public void setStorageUomHeight(Float storageUomHeight) {
        this.storageUomHeight = storageUomHeight;
    }

    public Float getStorageUomWidth() {
        return storageUomWidth;
    }

    public void setStorageUomWidth(Float storageUomWidth) {
        this.storageUomWidth = storageUomWidth;
    }

    public Float getStorageUomGrossWeight() {
        return storageUomGrossWeight;
    }

    public void setStorageUomGrossWeight(Float storageUomGrossWeight) {
        this.storageUomGrossWeight = storageUomGrossWeight;
    }

    public List<SkuUomMapping> getSkuUomMappingList() {
        return skuUomMappingList;
    }

    public void setSkuUomMappingList(List<SkuUomMapping> skuUomMappingList) {
        this.skuUomMappingList = skuUomMappingList;
    }

}
