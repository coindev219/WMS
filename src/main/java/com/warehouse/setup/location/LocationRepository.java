package com.warehouse.setup.location;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long>{
    Optional<Location> findByCodeAndLocationHierarchyId(String code,Long locationHierarchyId);
    Optional<Location> findByCodeAndLocationHierarchyIdAndIdNot(String code,Long locationHierarchyId,Long id);

}
