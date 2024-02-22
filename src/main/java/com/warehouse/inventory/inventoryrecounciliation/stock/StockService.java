package com.warehouse.inventory.inventoryrecounciliation.stock;

import java.util.List;

public interface StockService {

    // Stock Api
    public String validateStockRequest(Stock theStockRequest);

    public List<Stock> saveStocks(List<Stock> theStockList);

    List<Stock> findAll();
}
