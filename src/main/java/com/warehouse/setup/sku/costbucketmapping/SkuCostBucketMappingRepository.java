package com.warehouse.setup.sku.costbucketmapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuCostBucketMappingRepository extends JpaRepository<SkuCostBucketMapping, Long> {
        Optional<SkuCostBucketMapping> findBySkuWarehouseMappingIdAndCostBucketId(Long skuWarehouseMappingId,
                        Long costBucketId);

        Optional<SkuCostBucketMapping> findBySkuWarehouseMappingIdAndCostBucketIdAndIdNot(Long skuWarehouseMappingId,
                        Long costBucketId, Long id);
}
