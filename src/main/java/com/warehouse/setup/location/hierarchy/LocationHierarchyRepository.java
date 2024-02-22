package com.warehouse.setup.location.hierarchy;

import java.util.List;
import java.util.Optional;

import com.warehouse.setup.location.locationtype.LocationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationHierarchyRepository extends JpaRepository<LocationHierarchy,Long>{
    Optional<LocationHierarchy> findByNameAndWarehouseIdAndLocationTypeId(String name,Long warehouseId,
                                    Long locationTypeId);
    Optional<LocationHierarchy> findByNameAndWarehouseIdAndLocationTypeIdAndIdNot(String name,Long warehouseId,
                                    Long locationTypeId,Long id);

    Optional<LocationHierarchy> findByCodeAndWarehouseIdAndLocationTypeId(String code,Long warehouseId,
                                    Long locationTypeId);
    Optional<LocationHierarchy> findByCodeAndWarehouseIdAndLocationTypeIdAndIdNot(String code,Long warehouseId,
                                    Long locationTypeId,Long id);

    List<LocationHierarchy> findByLocationType(LocationType locationType);
}
