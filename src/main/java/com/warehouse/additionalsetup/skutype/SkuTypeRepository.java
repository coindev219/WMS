package com.warehouse.additionalsetup.skutype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuTypeRepository extends JpaRepository<SkuType, Long> {
    Optional<SkuType> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    Optional<SkuType> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<SkuType> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<SkuType> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);
}
