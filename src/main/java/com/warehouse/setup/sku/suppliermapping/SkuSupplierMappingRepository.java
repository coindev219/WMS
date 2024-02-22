package com.warehouse.setup.sku.suppliermapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuSupplierMappingRepository extends JpaRepository<SkuSupplierMapping,Long>{
    Optional<SkuSupplierMapping> findByPrimaryCompanyIdAndSupplierIdAndSkuId(Long primaryCompanyId,Long supplierId,Long skuId);
    Optional<SkuSupplierMapping> findByPrimaryCompanyIdAndSupplierIdAndSkuIdAndIdNot(Long primaryCompanyId,Long supplierId,Long skuId,Long id);
}
