package com.warehouse.city;

import java.util.List;
import java.util.Optional;

import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAllCityByState(Long theStateId) {
        return cityRepository.findByStateId(theStateId);
    }

    @Override
    public City findById(Long theId) {
        return cityRepository.findById(theId)
        .orElseThrow(() -> new ResourceNotFoundException("City", "id", theId));
    }

    @Override
    public City save(City theCity) {
        boolean isCityExists = false;
        if (theCity.getId() == null){
            if (cityRepository.findByCityName(theCity.getCityName()).isPresent()){
                isCityExists = true;
            }
        }else{
            if (cityRepository.findByCityNameAndIdNot(theCity.getCityName(),theCity.getId()).isPresent()){
                isCityExists = true;
            }
        }
        if(isCityExists){
            throw new DuplicateValueFoundException("City", "cityName", theCity.getCityName());
        }

        return cityRepository.save(theCity);
    }

    @Override
    public void deleteById(Long theId) {
        cityRepository.deleteById(theId);
    }

    @Override
    public Optional<City> findByIdAndStateId(Long id, Long stateId) {
        return cityRepository.findByIdAndStateId(id, stateId);
    }

    @Override
    public List<City> findAllForMoibleApps(Long theStateId) {
       return cityRepository.findAllCitiesForMobileApp(theStateId);
    }
    
}
