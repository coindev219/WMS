package com.warehouse.setup.customer.allocationpriorityrules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseWiseAllocationPriorityRulesRepository
        extends JpaRepository<WarehouseWiseAllocationPriorityRules, Long> {
}
