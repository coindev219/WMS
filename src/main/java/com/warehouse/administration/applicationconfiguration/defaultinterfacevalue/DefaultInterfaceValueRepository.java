package com.warehouse.administration.applicationconfiguration.defaultinterfacevalue;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultInterfaceValueRepository extends JpaRepository<DefaultInterfaceValue,Long>{
    Optional<DefaultInterfaceValue> findByConfigKeyAndPrimaryCompanyId(String configKey,Long primaryCompanyId);
    Optional<DefaultInterfaceValue> findByConfigKeyAndPrimaryCompanyIdAndIdNot(String configKey,Long primaryCompanyId,Long id);
}
