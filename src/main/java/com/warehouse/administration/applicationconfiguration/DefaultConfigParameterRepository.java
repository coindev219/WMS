package com.warehouse.administration.applicationconfiguration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultConfigParameterRepository extends JpaRepository<DefaultConfigParameters,Long>{
    List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrue();
    List<DefaultConfigParameters> findByIsWarehouseConfigTrue();
    List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue();

}
