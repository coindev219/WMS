package com.warehouse.currency;

import java.util.List;

import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    private CurrencyRepository currencyRepository;


    @Override
    public List<Currency> findAllCurrency() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency findById(Long theId) {
        return currencyRepository.findById(theId)
        .orElseThrow(() -> new ResourceNotFoundException("Currency", "id", theId));
    }

    @Override
    public Currency save(Currency theCurrency){
        boolean isCurrencyExists = false;
        if (theCurrency.getId() == null){
            if (currencyRepository.findByName(theCurrency.getName()).isPresent()){
                isCurrencyExists = true;
            }
        }else{
            if (currencyRepository.findByNameAndIdNot(theCurrency.getName(),theCurrency.getId()).isPresent()){
                isCurrencyExists = true;
            }
        }
        if(isCurrencyExists){
            throw new DuplicateValueFoundException("Currency", "CurrencyName", theCurrency.getName());
        }

        return currencyRepository.save(theCurrency);
    }

    @Override
    public void deleteById(Long theId) {
        currencyRepository.deleteById(theId);
    }
    
}
