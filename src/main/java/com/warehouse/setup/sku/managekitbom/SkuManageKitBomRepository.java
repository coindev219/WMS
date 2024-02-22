package com.warehouse.setup.sku.managekitbom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuManageKitBomRepository extends JpaRepository<SkuManageKitBom,Long> {
    
}
