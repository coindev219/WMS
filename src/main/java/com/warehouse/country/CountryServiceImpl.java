package com.warehouse.country;

import java.util.List;

import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long theId) {
        return countryRepository.findById(theId)
            .orElseThrow(() -> new ResourceNotFoundException("Country", "id", theId));
    }

    @Override
    public Country save(Country theCountry) {
        boolean isCountryExists = false;
        if (theCountry.getId() == null){
            if (countryRepository.findByCountryName(theCountry.getCountryName()).isPresent()){
                isCountryExists = true;
            }
        }else{
            if (countryRepository.findByCountryNameAndIdNot(theCountry.getCountryName(),theCountry.getId()).isPresent()){
                isCountryExists = true;
            }
        }
        if(isCountryExists){
            throw new DuplicateValueFoundException("Country", "countryName", theCountry.getCountryName());
        }
        return countryRepository.save(theCountry);
    }

    @Override
    public void deleteById(Long theId) {
        countryRepository.deleteById(theId);
    }

    @Override
    public boolean isCountryExists(Long theId) {
       return countryRepository.existsById(theId);
    }

    @Override
    public List<Country> findAllForMoibleApps() {
        return countryRepository.findAllCountriesForMobileApps();
    }
    
}
