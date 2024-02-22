package com.warehouse.setup.customer.maxage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWiseMaxAgeRepository extends JpaRepository<CustomerWiseMaxAge,Long>{
    Optional<CustomerWiseMaxAge> findByCustomerIdAndSkuId(Long customerId,Long skuId);
    Optional<CustomerWiseMaxAge> findByCustomerIdAndSkuIdAndIdNot(Long customerId,Long skuId,Long id);
}
