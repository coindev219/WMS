package com.warehouse.administration.applicationconfiguration.primarycomapnyparameters;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryCompanyConfigRepository extends JpaRepository<PrimaryCompanyConfig,Long>{
    Optional<PrimaryCompanyConfig> findByConfigKeyAndPrimaryCompanyId(String configKey,Long primaryCompanyId);
    Optional<PrimaryCompanyConfig> findByConfigKeyAndPrimaryCompanyIdAndIdNot(String configKey,Long primaryCompanyId,Long id);
}
