package com.warehouse.savedsearch;

import java.util.List;

import com.warehouse.enums.SavedSearchFilterTypeEnum;

public interface SavedSearchService {
    
     public List<SavedSearch> findAllSavedSearchByFilterType(SavedSearchFilterTypeEnum filterType);
	
	 public SavedSearch findById(Long theId);
	
	 public SavedSearch save(SavedSearch theSavedSearch);
	
	 public void deleteById(Long theId);

	 public void deleteAllSavedSearchs(SavedSearchFilterTypeEnum filterType);
}
