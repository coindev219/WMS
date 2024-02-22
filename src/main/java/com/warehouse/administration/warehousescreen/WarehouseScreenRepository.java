package com.warehouse.administration.warehousescreen;

import java.util.List;

import com.warehouse.enums.UserTypeEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseScreenRepository extends JpaRepository<WarehouseScreen, Long> {
    List<WarehouseScreen> findAllByUserType(UserTypeEnum userType);
}
