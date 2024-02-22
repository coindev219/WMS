package com.warehouse.currency;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long>{
    Optional<Currency> findByName(String name);
    Optional<Currency> findByNameAndIdNot(String name,Long id);

}
