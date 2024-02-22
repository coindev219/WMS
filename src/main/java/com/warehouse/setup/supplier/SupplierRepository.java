package com.warehouse.setup.supplier;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<Supplier> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<Supplier> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<Supplier> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);

    Optional<Supplier> findByContactNameAndPrimaryCompanyId(String contactName, Long primaryCompanyId);

    Optional<Supplier> findByContactNameAndPrimaryCompanyIdAndIdNot(String contactName, Long primaryCompanyId, Long id);
}
