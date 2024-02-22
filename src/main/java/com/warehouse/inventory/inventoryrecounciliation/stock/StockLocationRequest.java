package com.warehouse.inventory.inventoryrecounciliation.stock;

public class StockLocationRequest {

    private Long id;

    private Long stockId;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "StockLocationRequest{" + "id=" + id + ", stockId=" + stockId + ", locationId=" + locationId + '}';
    }
}
