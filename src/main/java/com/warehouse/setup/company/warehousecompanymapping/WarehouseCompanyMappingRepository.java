package com.warehouse.setup.company.warehousecompanymapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseCompanyMappingRepository extends JpaRepository<WarehouseCompanyMapping,Long>{
    Optional<WarehouseCompanyMapping> findByPrimaryCompanyIdAndWarehouseId(Long primaryCompanyId,Long warehouseId);
    Optional<WarehouseCompanyMapping> findByPrimaryCompanyIdAndWarehouseIdAndIdNot(Long primaryCompanyId,Long warehouseId,Long id);

    Optional<WarehouseCompanyMapping> findByPrimaryCompanyIdAndWarehouseIdAndShipTo(Long primaryCompanyId,Long warehouseId,String shipTo);
    Optional<WarehouseCompanyMapping> findByPrimaryCompanyIdAndWarehouseIdAndShipToAndIdNot(Long primaryCompanyId,Long warehouseId,String shipTo,Long id);

}
