package com.warehouse.setup.sku.uommapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuUomMappingRepository extends JpaRepository<SkuUomMapping,Long>{
    Optional<SkuUomMapping> findByUomIdAndSkuId(Long uomId,Long skuId);
    Optional<SkuUomMapping> findByUomIdAndSkuIdAndIdNot(Long uomId,Long skuId,Long id);
    Optional<SkuUomMapping> findByIsDefaultSalesOrderingUomTrueAndSkuId(Long skuId);
    Optional<SkuUomMapping> findByIsDefaultSalesOrderingUomTrueAndSkuIdAndIdNot(Long skuId,Long id);

    Optional<SkuUomMapping> findByIsDefaultSalesBillingUomTrueAndSkuId(Long skuId);
    Optional<SkuUomMapping> findByIsDefaultSalesBillingUomTrueAndSkuIdAndIdNot(Long skuId,Long id);

    Optional<SkuUomMapping> findByIsDefaultPurchaseOrderingUomTrueAndSkuId(Long skuId);
    Optional<SkuUomMapping> findByIsDefaultPurchaseOrderingUomTrueAndSkuIdAndIdNot(Long skuId,Long id);

    Optional<SkuUomMapping> findByIsDefaultPurchaseBillingUomTrueAndSkuId(Long skuId);
    Optional<SkuUomMapping> findByIsDefaultPurchaseBillingUomTrueAndSkuIdAndIdNot(Long skuId,Long id);
}
