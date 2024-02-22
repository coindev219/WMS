package com.warehouse.setup.location.restriction;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRestrictionRepository extends JpaRepository<LocationRestriction,Long>{
    Optional<LocationRestriction> findByName(String name);
    Optional<LocationRestriction> findByNameAndIdNot(String name,Long id);

    Optional<LocationRestriction> findByCode(String code);
    Optional<LocationRestriction> findByCodeAndIdNot(String code,Long id);
}
