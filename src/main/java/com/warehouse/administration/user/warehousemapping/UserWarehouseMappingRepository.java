package com.warehouse.administration.user.warehousemapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWarehouseMappingRepository extends JpaRepository<UserWarehouseMapping,Long>{
    Optional<UserWarehouseMapping> findByUserIdAndWarehouseId(Long userId,Long warehouseId);
    Optional<UserWarehouseMapping> findByUserIdAndWarehouseIdAndIdNot(Long userId,Long warehouseId,Long id);
}
