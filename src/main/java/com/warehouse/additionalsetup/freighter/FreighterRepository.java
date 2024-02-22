package com.warehouse.additionalsetup.freighter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreighterRepository extends JpaRepository<Freighter, Long> {

    Optional<Freighter> findByName(String name);
    Optional<Freighter> findByNameAndIdNot(String name,Long id);
    Optional<Freighter> findByCode(String code);
    Optional<Freighter> findByCodeAndIdNot(String code,Long id);

}
