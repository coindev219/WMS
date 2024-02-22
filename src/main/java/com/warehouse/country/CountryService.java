package com.warehouse.country;

import java.util.List;

public interface CountryService {
    
    public List<Country> findAll();
	
	public Country findById(Long theId);
	
	public Country save(Country theCountry);
	
	public void deleteById(Long theId);

	public boolean isCountryExists(Long theId);

	public List<Country> findAllForMoibleApps();

}
