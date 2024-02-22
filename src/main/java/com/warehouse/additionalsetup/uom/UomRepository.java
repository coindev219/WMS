package com.warehouse.additionalsetup.uom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UomRepository extends JpaRepository<Uom, Long> {
    Optional<Uom> findByNameAndCreatedById(String name, Long createdById);

    Optional<Uom> findByNameAndCreatedByIdAndIdNot(String name, Long createdById, Long id);

    Optional<Uom> findByUomAndCreatedById(String uom, Long createdById);

    Optional<Uom> findByUomAndCreatedByIdAndIdNot(String uom, Long createdById, Long id);
}
