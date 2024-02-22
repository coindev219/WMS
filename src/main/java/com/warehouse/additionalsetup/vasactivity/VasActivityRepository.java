package com.warehouse.additionalsetup.vasactivity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VasActivityRepository extends JpaRepository<VasActivity, Long> {

    Optional<VasActivity> findByNameAndWarehouseId(String name,Long warehouseId);
    Optional<VasActivity> findByNameAndWarehouseIdAndIdNot(String name,Long warehouseId,Long id);
    Optional<VasActivity> findByCodeAndWarehouseId(String code,Long warehouseId);
    Optional<VasActivity> findByCodeAndWarehouseIdAndIdNot(String code,Long warehouseId,Long id);

}
