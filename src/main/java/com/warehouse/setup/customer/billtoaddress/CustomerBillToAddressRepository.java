package com.warehouse.setup.customer.billtoaddress;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBillToAddressRepository extends JpaRepository<CustomerBillToAddress, Long> {
    Optional<CustomerBillToAddress> findByBillToAndCustomerId(String billTo, Long customerId);

    Optional<CustomerBillToAddress> findByBillToAndCustomerIdAndIdNot(String billTo, Long customerId, Long id);

    Optional<CustomerBillToAddress> findByContactNameAndCustomerId(String contactName, Long customerId);

    Optional<CustomerBillToAddress> findByContactNameAndCustomerIdAndIdNot(String contactName, Long customerId,
            Long id);

}
