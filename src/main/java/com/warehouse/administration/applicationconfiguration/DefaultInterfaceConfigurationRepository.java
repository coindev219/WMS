package com.warehouse.administration.applicationconfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultInterfaceConfigurationRepository extends JpaRepository<DefaultInterfaceConfiguration,Long>{
    
}
