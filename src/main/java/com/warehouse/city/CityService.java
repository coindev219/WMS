package com.warehouse.city;

import java.util.List;
import java.util.Optional;

public interface CityService {
    
    public List<City> findAllCityByState(Long theStateId);
	
	public City findById(Long theId);
	
	public City save(City theCity);
	
	public void deleteById(Long theId);

	Optional<City> findByIdAndStateId(Long id, Long stateId);

	public List<City> findAllForMoibleApps(Long theStateId);
}
