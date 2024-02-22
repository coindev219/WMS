package com.warehouse.setup.sku;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<Sku,Long>{
    Optional<Sku> findByNameAndPrimaryCompanyId(String name,Long primaryCompanyId);
    Optional<Sku> findByNameAndPrimaryCompanyIdAndIdNot(String name,Long primaryCompanyId,Long id);

    Optional<Sku> findByCodeAndPrimaryCompanyId(String code,Long primaryCompanyId);
    Optional<Sku> findByCodeAndPrimaryCompanyIdAndIdNot(String code,Long primaryCompanyId,Long id);
}
