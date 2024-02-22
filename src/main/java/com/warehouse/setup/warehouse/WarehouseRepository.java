package com.warehouse.setup.warehouse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{
    Optional<Warehouse> findByName(String name);
    Optional<Warehouse> findByNameAndIdNot(String name,Long id);

    Optional<Warehouse> findByCode(String code);
    Optional<Warehouse> findByCodeAndIdNot(String code,Long id);

    Optional<Warehouse> findByContactName(String contactName);
    Optional<Warehouse> findByContactNameAndIdNot(String contactName,Long id);
}
