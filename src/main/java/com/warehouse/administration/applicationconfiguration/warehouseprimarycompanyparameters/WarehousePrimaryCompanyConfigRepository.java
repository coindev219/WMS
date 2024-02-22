package com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehousePrimaryCompanyConfigRepository extends JpaRepository<WarehousePrimaryCompanyConfig,Long>{
    Optional<WarehousePrimaryCompanyConfig> findByConfigKeyAndWarehouseCompanyMappingId(String configKey,Long warehouseCompanyMappingId);
    Optional<WarehousePrimaryCompanyConfig> findByConfigKeyAndWarehouseCompanyMappingIdAndIdNot(String configKey,Long warehouseCompanyMappingId,Long id);
}
