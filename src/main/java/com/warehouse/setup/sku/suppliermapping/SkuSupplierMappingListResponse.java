package com.warehouse.setup.sku.suppliermapping;

import java.util.List;

public class SkuSupplierMappingListResponse {

    private Long supplierId;
    private String suppplierCode;
    private String supplierName;
    private Long primaryCompanyId;
    private String primaryCompanyCode;
    private String primaryCompanyName;
    private List<SkuSupplierMapping> skuSupplierMappingList;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSuppplierCode() {
        return suppplierCode;
    }

    public void setSuppplierCode(String suppplierCode) {
        this.suppplierCode = suppplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public List<SkuSupplierMapping> getSkuSupplierMappingList() {
        return skuSupplierMappingList;
    }

    public void setSkuSupplierMappingList(List<SkuSupplierMapping> skuSupplierMappingList) {
        this.skuSupplierMappingList = skuSupplierMappingList;
    }

    public SkuSupplierMappingListResponse() {
    }

    public SkuSupplierMappingListResponse(Long supplierId, String suppplierCode, String supplierName,
            Long primaryCompanyId, String primaryCompanyCode, String primaryCompanyName) {
        this.supplierId = supplierId;
        this.suppplierCode = suppplierCode;
        this.supplierName = supplierName;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyName = primaryCompanyName;
    }

    @Override
    public String toString() {
        return "SkuWarehouseMappingListResponse [primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", skuSupplierMappingList="
                + skuSupplierMappingList + ", supplierId=" + supplierId + ", supplierName=" + supplierName
                + ", suppplierCode=" + suppplierCode + "]";
    }

}
