package com.warehouse.state;

import java.util.List;
import java.util.Optional;

public interface StateService {
    
    public List<State> findAllStatesByCountry(Long theCountryId);
	
	public State findById(Long theId);
	
	public State save(State theState);
	
	public void deleteById(Long theId);

	Optional<State> findByIdAndCountryId(Long id, Long countryId);

	public boolean isStateExists(Long theId);

	public List<State> findAllForMoibleApps(Long theCountryId);
}
