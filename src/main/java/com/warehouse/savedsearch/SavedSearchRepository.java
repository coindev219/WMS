package com.warehouse.savedsearch;

import java.util.List;
import java.util.Optional;

import com.warehouse.enums.SavedSearchFilterTypeEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedSearchRepository extends JpaRepository<SavedSearch,Long>{
    List<SavedSearch> findByFilterType(SavedSearchFilterTypeEnum filterType);
    Optional<SavedSearch> findBySearchNameAndFilterType(String searchName,SavedSearchFilterTypeEnum filterType);
    Optional<SavedSearch> findBySearchNameAndFilterTypeAndIdNot(String searchName,
                        SavedSearchFilterTypeEnum filterType,Long id);
}
