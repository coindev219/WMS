package com.warehouse.setup.location.dimension;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DimensionDefinitionRepository extends JpaRepository<DimensionDefinition,Long>{
    Optional<DimensionDefinition> findByName(String name);
    Optional<DimensionDefinition> findByNameAndIdNot(String name,Long id);

    Optional<DimensionDefinition> findByCode(String code);
    Optional<DimensionDefinition> findByCodeAndIdNot(String code,Long id);
}
