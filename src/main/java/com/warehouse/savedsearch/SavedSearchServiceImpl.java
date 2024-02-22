package com.warehouse.savedsearch;

import java.util.List;

import com.warehouse.enums.SavedSearchFilterTypeEnum;
import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavedSearchServiceImpl implements SavedSearchService{

    @Autowired
    private SavedSearchRepository savedSearchRepository;

    @Override
    public List<SavedSearch> findAllSavedSearchByFilterType(SavedSearchFilterTypeEnum filterType) {
        return savedSearchRepository.findByFilterType(filterType);
    }

    @Override
    public SavedSearch findById(Long theId) {
        return savedSearchRepository.findById(theId).
                orElseThrow(() -> new ResourceNotFoundException("SavedSearch", "id", theId));
        
    }

    @Override
    public SavedSearch save(SavedSearch theSavedSearch) {
        if (theSavedSearch.getId() == null){
            if (savedSearchRepository.findBySearchNameAndFilterType(theSavedSearch.getSearchName(),theSavedSearch.getFilterType()).isPresent()){
                throw new DuplicateValueFoundException("SavedSearch", "searchName", theSavedSearch.getSearchName());
            }
        }else{

            if (savedSearchRepository.findBySearchNameAndFilterTypeAndIdNot(theSavedSearch.getSearchName(),
                    theSavedSearch.getFilterType(),theSavedSearch.getId()).isPresent()){
                throw new DuplicateValueFoundException("SavedSearch", "searchName", theSavedSearch.getSearchName());
            }
        }
        
        return savedSearchRepository.save(theSavedSearch);
    }

    @Override
    public void deleteById(Long theId) {
        savedSearchRepository.deleteById(theId);
    }

    @Override
    public void deleteAllSavedSearchs(SavedSearchFilterTypeEnum filterType) {
        List<SavedSearch> savedSearchs = savedSearchRepository.findByFilterType(filterType);
        for (SavedSearch savedSearch : savedSearchs) {
            savedSearchRepository.deleteById(savedSearch.getId());
        }
    }
    
}
