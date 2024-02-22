package com.warehouse.country;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country,Long>{
    Optional<Country> findByCountryName(String countryName);
    Optional<Country> findByCountryNameAndIdNot(String countryName,Long id);
    
    @Query("select new Country(c.id,c.countryName) from Country c")
    List<Country> findAllCountriesForMobileApps();
}

