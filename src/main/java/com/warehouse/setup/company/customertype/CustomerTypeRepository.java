package com.warehouse.setup.company.customertype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
    Optional<CustomerType> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<CustomerType> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<CustomerType> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<CustomerType> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);
}
