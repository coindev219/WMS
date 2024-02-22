package com.warehouse.setup.company.reason;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {
    Optional<Reason> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<Reason> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<Reason> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<Reason> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);
}
