package com.warehouse.sales.salesorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderSkuRepository
        extends JpaRepository<SalesOrderSku, Long> {
                
}
