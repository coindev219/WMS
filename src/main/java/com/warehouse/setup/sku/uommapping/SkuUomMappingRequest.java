package com.warehouse.setup.sku.uommapping;

import com.warehouse.enums.ComplexityEnum;

public class SkuUomMappingRequest {

    private Long id;

    private Long primaryCompanyId;

    private Long skuId;

    private Long uomId;

    Float multiplicationFactorToConvertStorageUom;

    boolean isDefaultSalesOrderingUom;

    boolean isDefaultSalesBillingUom;

    boolean isDefaultPurchaseOrderingUom;

    boolean isDefaultPurchaseBillingUom;

    String barcode;

    private Float purchaseLotSize;

    private Float salesLotSize;

    private Integer minPoQty;

    private Integer maxPoQty;

    private Integer minSoQty;

    private Integer maxSoQty;

    private Float netWeight;

    private Boolean weightRequired;

    private ComplexityEnum inComplexity = ComplexityEnum.Select;

    private ComplexityEnum outComplexity = ComplexityEnum.Select;

    private Long mheTypeId;

    private Float length;

    private Float height;

    private Float width;

    private Float grossWeight;

    private Long storageCategoryId;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String searchFor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Float getMultiplicationFactorToConvertStorageUom() {
        return multiplicationFactorToConvertStorageUom;
    }

    public void setMultiplicationFactorToConvertStorageUom(Float multiplicationFactorToConvertStorageUom) {
        this.multiplicationFactorToConvertStorageUom = multiplicationFactorToConvertStorageUom;
    }

    public boolean isDefaultSalesOrderingUom() {
        return isDefaultSalesOrderingUom;
    }

    public void setDefaultSalesOrderingUom(boolean isDefaultSalesOrderingUom) {
        this.isDefaultSalesOrderingUom = isDefaultSalesOrderingUom;
    }

    public boolean isDefaultSalesBillingUom() {
        return isDefaultSalesBillingUom;
    }

    public void setDefaultSalesBillingUom(boolean isDefaultSalesBillingUom) {
        this.isDefaultSalesBillingUom = isDefaultSalesBillingUom;
    }

    public boolean isDefaultPurchaseOrderingUom() {
        return isDefaultPurchaseOrderingUom;
    }

    public void setDefaultPurchaseOrderingUom(boolean isDefaultPurchaseOrderingUom) {
        this.isDefaultPurchaseOrderingUom = isDefaultPurchaseOrderingUom;
    }

    public boolean isDefaultPurchaseBillingUom() {
        return isDefaultPurchaseBillingUom;
    }

    public void setDefaultPurchaseBillingUom(boolean isDefaultPurchaseBillingUom) {
        this.isDefaultPurchaseBillingUom = isDefaultPurchaseBillingUom;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getPurchaseLotSize() {
        return purchaseLotSize;
    }

    public void setPurchaseLotSize(Float purchaseLotSize) {
        this.purchaseLotSize = purchaseLotSize;
    }

    public Float getSalesLotSize() {
        return salesLotSize;
    }

    public void setSalesLotSize(Float salesLotSize) {
        this.salesLotSize = salesLotSize;
    }

    public Integer getMinPoQty() {
        return minPoQty;
    }

    public void setMinPoQty(Integer minPoQty) {
        this.minPoQty = minPoQty;
    }

    public Integer getMaxPoQty() {
        return maxPoQty;
    }

    public void setMaxPoQty(Integer maxPoQty) {
        this.maxPoQty = maxPoQty;
    }

    public Integer getMinSoQty() {
        return minSoQty;
    }

    public void setMinSoQty(Integer minSoQty) {
        this.minSoQty = minSoQty;
    }

    public Integer getMaxSoQty() {
        return maxSoQty;
    }

    public void setMaxSoQty(Integer maxSoQty) {
        this.maxSoQty = maxSoQty;
    }

    public Float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    public Boolean getWeightRequired() {
        return weightRequired;
    }

    public void setWeightRequired(Boolean weightRequired) {
        this.weightRequired = weightRequired;
    }

    public ComplexityEnum getInComplexity() {
        return inComplexity;
    }

    public void setInComplexity(ComplexityEnum inComplexity) {
        this.inComplexity = inComplexity;
    }

    public ComplexityEnum getOutComplexity() {
        return outComplexity;
    }

    public void setOutComplexity(ComplexityEnum outComplexity) {
        this.outComplexity = outComplexity;
    }

    public Long getMheTypeId() {
        return mheTypeId;
    }

    public void setMheTypeId(Long mheTypeId) {
        this.mheTypeId = mheTypeId;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Long getStorageCategoryId() {
        return storageCategoryId;
    }

    public void setStorageCategoryId(Long storageCategoryId) {
        this.storageCategoryId = storageCategoryId;
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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }
}
