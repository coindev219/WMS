package com.warehouse.setup.sku.managekitbom;

import java.util.ArrayList;
import java.util.List;

public class ManageKitBomHeaderRequest {

    private Long kitSkuId;
    private Float purchaseUnitPrice;
    private Float salesUnitPrice;

    private List<SkuManageKitBom> skuManageKitBomList = new ArrayList<SkuManageKitBom>();

    public Long getKitSkuId() {
        return kitSkuId;
    }

    public void setKitSkuId(Long kitSkuId) {
        this.kitSkuId = kitSkuId;
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

    public List<SkuManageKitBom> getSkuManageKitBomList() {
        return skuManageKitBomList;
    }

    public void setSkuManageKitBomList(List<SkuManageKitBom> skuManageKitBomList) {
        this.skuManageKitBomList = skuManageKitBomList;
    }

    @Override
    public String toString() {
        return "ManageKitBomHeaderRequest [kitSkuId=" + kitSkuId + ", purchaseUnitPrice=" + purchaseUnitPrice
                + ", salesUnitPrice=" + salesUnitPrice + ", skuManageKitBomList=" + skuManageKitBomList + "]";
    }

    

}
