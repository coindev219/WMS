package com.warehouse.setup.location.locationtype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType,Long>{
    Optional<LocationType> findByName(String name);
    Optional<LocationType> findByNameAndIdNot(String name,Long id);
}
