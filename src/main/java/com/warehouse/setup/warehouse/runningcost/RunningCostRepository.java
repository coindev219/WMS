package com.warehouse.setup.warehouse.runningcost;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunningCostRepository extends JpaRepository<RunningCost,Long>{
    Optional<RunningCost> findByCostAndWarehouseId(Float cost,Long warehouseId);
    Optional<RunningCost> findByCostAndWarehouseIdAndIdNot(Float cost,Long warehouseId,Long id);
}
