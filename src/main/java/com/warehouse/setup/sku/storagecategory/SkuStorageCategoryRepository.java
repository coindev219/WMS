package com.warehouse.setup.sku.storagecategory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SkuStorageCategoryRepository extends JpaRepository<SkuStorageCategory,Long>{
    Optional<SkuStorageCategory> findByNameAndPrimaryCompanyId(String name,Long primaryCompanyId);
    Optional<SkuStorageCategory> findByNameAndPrimaryCompanyIdAndIdNot(String name,Long primaryCompanyId,Long id);

    Optional<SkuStorageCategory> findByCodeAndPrimaryCompanyId(String code,Long primaryCompanyId);
    Optional<SkuStorageCategory> findByCodeAndPrimaryCompanyIdAndIdNot(String code,Long primaryCompanyId,Long id);
    
}
