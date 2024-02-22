package com.warehouse.setup.company.packinggroup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackingGroupRepository extends JpaRepository<PackingGroup, Long> {
    Optional<PackingGroup> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<PackingGroup> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<PackingGroup> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<PackingGroup> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);
}
