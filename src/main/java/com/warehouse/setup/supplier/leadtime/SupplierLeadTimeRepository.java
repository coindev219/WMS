package com.warehouse.setup.supplier.leadtime;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierLeadTimeRepository extends JpaRepository<SupplierLeadTime, Long> {
    Optional<SupplierLeadTime> findByWarehouseIdAndPrimaryCompanyIdAndSupplierId(long warehouseId, long supplierId,
            Long primaryCompanyId);

    Optional<SupplierLeadTime> findByWarehouseIdAndPrimaryCompanyIdAndSupplierIdAndIdNot(long warehouseId,
            long supplierId, Long primaryCompanyId, Long id);

}
