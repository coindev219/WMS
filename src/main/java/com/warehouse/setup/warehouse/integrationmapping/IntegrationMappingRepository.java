package com.warehouse.setup.warehouse.integrationmapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationMappingRepository extends JpaRepository<IntegrationMapping,Long>{
    Optional<IntegrationMapping> findByCodeAndWarehouseId(String code,Long warehouseId);
    Optional<IntegrationMapping> findByCodeAndWarehouseIdAndIdNot(String code,Long warehouseId,Long id);
}
