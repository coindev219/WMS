package com.warehouse.city;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long>{
    List<City> findByStateId(Long theStateId);
    Optional<City> findByIdAndStateId(Long id, Long stateId);
    Optional<City> findByCityName(String cityName);
    Optional<City> findByCityNameAndIdNot(String cityName,Long id);

    @Query("select new City(c.id,c.cityName) from City c where c.stateId = ?1")
    List<City> findAllCitiesForMobileApp(Long stateId);

}
