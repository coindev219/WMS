package com.warehouse.state;

import java.util.List;
import java.util.Optional;

import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService{

    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<State> findAllStatesByCountry(Long theCountryId) {
        return stateRepository.findByCountryId(theCountryId);
    }

    @Override
    public State findById(Long theId) {
        return stateRepository.findById(theId)
            .orElseThrow(() -> new ResourceNotFoundException("State", "id", theId));
    }

    @Override
    public State save(State theState) {
        boolean isStateExists = false;
        if (theState.getId() == null){
            if (stateRepository.findByStateName(theState.getStateName()).isPresent()){
                isStateExists = true;
            }
        }else{
            if (stateRepository.findByStateNameAndIdNot(theState.getStateName(),theState.getId()).isPresent()){
                isStateExists = true;
            }
        }
        if(isStateExists){
            throw new DuplicateValueFoundException("State", "stateName", theState.getStateName());
        }

        return stateRepository.save(theState);
    }

    @Override
    public void deleteById(Long theId) {
        stateRepository.deleteById(theId);
        
    }

    @Override
    public Optional<State> findByIdAndCountryId(Long id, Long countryId) {
       return stateRepository.findByIdAndCountryId(id, countryId);
    }

    @Override
    public boolean isStateExists(Long theId) {
        return stateRepository.existsById(theId);
    }

    @Override
    public List<State> findAllForMoibleApps(Long theCountryId) {
        return stateRepository.findAllStateForMobileApp(theCountryId);
    }

    
    
}
