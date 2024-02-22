package com.warehouse.administration.applicationconfiguration.warehouseparameters;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseConfigRepository extends JpaRepository<WarehouseConfig,Long>{
    Optional<WarehouseConfig> findByConfigKeyAndWarehouseId(String configKey,Long warehouseId);
    Optional<WarehouseConfig> findByConfigKeyAndWarehouseIdAndIdNot(String configKey,Long warehouseId,Long id);
}
