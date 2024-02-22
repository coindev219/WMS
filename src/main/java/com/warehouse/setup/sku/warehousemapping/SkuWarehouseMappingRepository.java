package com.warehouse.setup.sku.warehousemapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuWarehouseMappingRepository extends JpaRepository<SkuWarehouseMapping, Long> {
    Optional<SkuWarehouseMapping> findByWarehouseCompanyMappingIdAndSkuId(Long warehouseCompanyMappingId, Long skuId);

    Optional<SkuWarehouseMapping> findByWarehouseCompanyMappingIdAndSkuIdAndIdNot(Long warehouseCompanyMappingId,
            Long skuId, Long id);
}
