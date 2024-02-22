package com.warehouse.administration.applicationconfiguration.organizationparameters;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationConfigRepository extends JpaRepository<OrganizationConfig,Long>{
    Optional<OrganizationConfig> findByConfigKeyAndOrganizationId(String configKey,Long organizationId);
    Optional<OrganizationConfig> findByConfigKeyAndOrganizationIdAndIdNot(String configKey,Long organizationId,Long id);
}
