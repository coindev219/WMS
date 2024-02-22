package com.warehouse.state;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StateRepository extends JpaRepository<State,Long>{
    List<State> findByCountryId(Long theCountryId);
    Optional<State> findByIdAndCountryId(Long id, Long countryId);
    Optional<State> findByStateName(String stateName);
    Optional<State> findByStateNameAndIdNot(String stateName,Long id);

    @Query("select new State(s.id,s.stateName) from State s where s.countryId = ?1")
    List<State> findAllStateForMobileApp(Long countryId);

}
