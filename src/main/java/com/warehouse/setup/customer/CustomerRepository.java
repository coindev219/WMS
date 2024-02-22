package com.warehouse.setup.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<Customer> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<Customer> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<Customer> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);

    Optional<Customer> findByContactNameAndPrimaryCompanyId(String contactName, Long primaryCompanyId);

    Optional<Customer> findByContactNameAndPrimaryCompanyIdAndIdNot(String contactName, Long primaryCompanyId, Long id);
}
