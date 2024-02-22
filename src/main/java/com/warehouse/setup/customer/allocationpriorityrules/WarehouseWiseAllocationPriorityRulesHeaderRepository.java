package com.warehouse.setup.customer.allocationpriorityrules;

import java.util.Optional;

import com.warehouse.enums.ShipModeEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseWiseAllocationPriorityRulesHeaderRepository
        extends JpaRepository<WarehouseWiseAllocationPriorityRulesHeader, Long> {

    Optional<WarehouseWiseAllocationPriorityRulesHeader> findByPrimaryCompanyIdAndCostBucketIdAndShipToIdAndOrderTypeIdAndSkuTypeIdAndShippingMode(
            Long primaryCompanyId, Long costBucketId, Long shipToId, Long orderTypeId, Long skuTypeId,
            ShipModeEnum shippingMode);
    Optional<WarehouseWiseAllocationPriorityRulesHeader> findByPrimaryCompanyIdAndCostBucketIdAndShipToIdAndOrderTypeIdAndSkuTypeIdAndShippingModeAndIdNot(
            Long primaryCompanyId, Long costBucketId, Long shipToId, Long orderTypeId, Long skuTypeId,
            ShipModeEnum shippingMode,Long id);

}
