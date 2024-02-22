package com.warehouse.inventory.inventoryrecounciliation.cycle;

public class CycleSkuRequest {

    private Long id;

    private Long cycleId;

    private Long skuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public String toString() {
        return "CycleSkuRequest{" + "id=" + id + ", cycleId=" + cycleId + ", skuId=" + skuId + '}';
    }
}
