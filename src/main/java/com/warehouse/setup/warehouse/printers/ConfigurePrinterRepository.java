package com.warehouse.setup.warehouse.printers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurePrinterRepository extends JpaRepository<ConfigurePrinter,Long>{
    Optional<ConfigurePrinter> findByNameAndWarehouseId(String name,Long warehouseId);
    Optional<ConfigurePrinter> findByNameAndWarehouseIdAndIdNot(String name,Long warehouseId,Long id);
}
