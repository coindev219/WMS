package com.warehouse.setup.sku.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SkuCategoryRepository extends JpaRepository<SkuCategory,Long>{
    Optional<SkuCategory> findByNameAndPrimaryCompanyId(String name,Long primaryCompanyId);
    Optional<SkuCategory> findByNameAndPrimaryCompanyIdAndIdNot(String name,Long primaryCompanyId,Long id);

    Optional<SkuCategory> findByCodeAndPrimaryCompanyId(String code,Long primaryCompanyId);
    Optional<SkuCategory> findByCodeAndPrimaryCompanyIdAndIdNot(String code,Long primaryCompanyId,Long id);
    
}
