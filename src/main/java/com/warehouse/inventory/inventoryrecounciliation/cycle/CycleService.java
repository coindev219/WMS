package com.warehouse.inventory.inventoryrecounciliation.cycle;

import java.util.List;

public interface CycleService {

    // Stock Api
    public String validateCycleRequest(Cycle theCycleRequest);

    public List<Cycle> saveCycles(List<Cycle> theCycleList);

    List<Cycle> findAll();
}
