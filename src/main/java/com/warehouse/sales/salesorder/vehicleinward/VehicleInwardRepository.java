package com.warehouse.sales.salesorder.vehicleinward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInwardRepository
        extends JpaRepository<VehicleInward, Long> {
                
}
