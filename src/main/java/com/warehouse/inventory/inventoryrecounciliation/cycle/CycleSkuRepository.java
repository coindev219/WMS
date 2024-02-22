package com.warehouse.inventory.inventoryrecounciliation.cycle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleSkuRepository extends JpaRepository<CycleSku, Long> {

}
