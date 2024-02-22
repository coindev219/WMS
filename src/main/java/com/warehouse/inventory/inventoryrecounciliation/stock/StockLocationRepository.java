package com.warehouse.inventory.inventoryrecounciliation.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {

}
