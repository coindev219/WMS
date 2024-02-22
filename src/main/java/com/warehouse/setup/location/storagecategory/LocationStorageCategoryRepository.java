package com.warehouse.setup.location.storagecategory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationStorageCategoryRepository extends JpaRepository<LocationStorageCategory,Long>{
    Optional<LocationStorageCategory> findByNameAndWarehouseId(String name,Long warehouseId);
    Optional<LocationStorageCategory> findByNameAndWarehouseIdAndIdNot(String name,Long warehouseId,Long id);

    Optional<LocationStorageCategory> findByCodeAndWarehouseId(String code,Long warehouseId);
    Optional<LocationStorageCategory> findByCodeAndWarehouseIdAndIdNot(String code,Long warehouseId,Long id);
    
}
