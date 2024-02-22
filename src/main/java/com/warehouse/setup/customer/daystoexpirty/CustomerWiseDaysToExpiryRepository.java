package com.warehouse.setup.customer.daystoexpirty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWiseDaysToExpiryRepository extends JpaRepository<CustomerWiseDaysToExpiry,Long>{
    Optional<CustomerWiseDaysToExpiry> findByCustomerIdAndSkuId(Long customerId,Long skuId);
    Optional<CustomerWiseDaysToExpiry> findByCustomerIdAndSkuIdAndIdNot(Long customerId,Long skuId,Long id);
}
