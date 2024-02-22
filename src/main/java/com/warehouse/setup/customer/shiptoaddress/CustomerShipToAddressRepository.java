package com.warehouse.setup.customer.shiptoaddress;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerShipToAddressRepository extends JpaRepository<CustomerShipToAddress, Long> {
    Optional<CustomerShipToAddress> findByShipToAndCustomerId(String shipTo, Long customerId);

    Optional<CustomerShipToAddress> findByShipToAndCustomerIdAndIdNot(String shipTo, Long customerId, Long id);

    Optional<CustomerShipToAddress> findByContactNameAndCustomerId(String contactName, Long customerId);

    Optional<CustomerShipToAddress> findByContactNameAndCustomerIdAndIdNot(String contactName, Long customerId,
            Long id);

}
