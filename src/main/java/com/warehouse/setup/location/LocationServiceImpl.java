package com.warehouse.setup.location;

import java.util.List;
import java.util.Objects;

import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.location.dimension.DimensionDefinition;
import com.warehouse.setup.location.dimension.DimensionDefinitionRepository;
import com.warehouse.setup.location.dimension.DimensionDefinitionRequest;
import com.warehouse.setup.location.dimension.DimensionDefinitionSearchRepository;
import com.warehouse.setup.location.hierarchy.LocationHierarchSearchRepository;
import com.warehouse.setup.location.hierarchy.LocationHierarchy;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRepository;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRequest;
import com.warehouse.setup.location.locationtype.LocationType;
import com.warehouse.setup.location.locationtype.LocationTypeRepository;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.restriction.LocationRestrictionRepository;
import com.warehouse.setup.location.restriction.LocationRestrictionRequest;
import com.warehouse.setup.location.restriction.LocationRestrictionSearchRepository;
import com.warehouse.setup.location.storagecategory.LocationStorageCategory;
import com.warehouse.setup.location.storagecategory.LocationStorageCategoryRepository;
import com.warehouse.setup.location.storagecategory.LocationStorageCategoryRequest;
import com.warehouse.setup.location.storagecategory.LocationStorageCategorySearchRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private DimensionDefinitionRepository  dimensionDefinitionRepository;
    
    @Autowired
    private DimensionDefinitionSearchRepository  dimensionDefinitionSearchRepository;

    @Autowired
    private LocationRestrictionRepository locationRestrictionRepository;

    @Autowired
    private LocationRestrictionSearchRepository locationRestrictionSearchRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private LocationHierarchyRepository locationHierarchyRepository;

    @Autowired
	private WarehouseService warehouseService;

    @Autowired
    private LocationHierarchSearchRepository locationHierarchSearchRepository;

    @Autowired
    private LocationStorageCategoryRepository locationStorageCategoryRepository;

    @Autowired
    private LocationStorageCategorySearchRepository locationStorageCategorySearchRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationSearchRepository locationSearchRepository;

    // Dimension Definition Implementation
    @Override
    public Page<DimensionDefinition> findAllDimensionDefinitionList(PageHelper page,
				DimensionDefinitionRequest request) {
        return dimensionDefinitionSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public DimensionDefinition findByDimensionDefinitionId(Long theId) {
        return dimensionDefinitionRepository.findById(theId).
            orElseThrow(() -> new ResourceNotFoundException("DimensionDefinition", "id", theId));
    }

    @Override
    public DimensionDefinition saveDimensionDefinition(DimensionDefinition theDimensionDefinition) {

        if (theDimensionDefinition.getId() == null){
            if (dimensionDefinitionRepository.findByName(theDimensionDefinition.getName()).isPresent()){
                throw new DuplicateValueFoundException("DimensionDefinition", "name", theDimensionDefinition.getName());
            }
            if (dimensionDefinitionRepository.findByCode(theDimensionDefinition.getCode()).isPresent()){
                throw new DuplicateValueFoundException("DimensionDefinition", "code", theDimensionDefinition.getCode());
            }
        }else{
            if (dimensionDefinitionRepository.findByNameAndIdNot(theDimensionDefinition.getName(),
                    theDimensionDefinition.getId()).isPresent()){
                throw new DuplicateValueFoundException("DimensionDefinition", "name", theDimensionDefinition.getName());
            }
            if (dimensionDefinitionRepository.findByCodeAndIdNot(theDimensionDefinition.getCode(),
                theDimensionDefinition.getId()).isPresent()){
                throw new DuplicateValueFoundException("DimensionDefinition", "code", theDimensionDefinition.getCode());
            }
        }

        return dimensionDefinitionRepository.save(theDimensionDefinition);
    }

    @Override
    public void deleteByDimensionDefinitionId(Long theId) {
        dimensionDefinitionRepository.deleteById(theId);
    }

    @Override
    public boolean isDimensionDefinitionExists(Long theId) {
        return dimensionDefinitionRepository.existsById(theId);
    }


    // Location Restriction Implementation
    
    @Override
    public Page<LocationRestriction> findAllLocationRestrictionList(PageHelper page,
                    LocationRestrictionRequest request) {
        return locationRestrictionSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public LocationRestriction findByLocationRestrictionId(Long theId) {
        return locationRestrictionRepository.findById(theId).
            orElseThrow(() -> new ResourceNotFoundException("LocationRestriction", "id", theId));
    }

    @Override
    public LocationRestriction saveLocationRestriction(LocationRestriction theLocationRestriction) {

        if (theLocationRestriction.getId() == null){
            if (locationRestrictionRepository.findByName(theLocationRestriction.getName()).isPresent()){
                throw new DuplicateValueFoundException("LocationRestriction", "name", theLocationRestriction.getName());
            }
            if (locationRestrictionRepository.findByCode(theLocationRestriction.getCode()).isPresent()){
                throw new DuplicateValueFoundException("LocationRestriction", "code", theLocationRestriction.getCode());
            }
        }else{
            if (locationRestrictionRepository.findByNameAndIdNot(theLocationRestriction.getName(),
                            theLocationRestriction.getId()).isPresent()){
                throw new DuplicateValueFoundException("LocationRestriction", "name", theLocationRestriction.getName());
            }
            if (locationRestrictionRepository.findByCodeAndIdNot(theLocationRestriction.getCode(),
                        theLocationRestriction.getId()).isPresent()){
                throw new DuplicateValueFoundException("LocationRestriction", "code", theLocationRestriction.getCode());
            }
        }

        return locationRestrictionRepository.save(theLocationRestriction);
    }

    @Override
    public void deleteByLocationRestrictionId(Long theId) {
        locationRestrictionRepository.deleteById(theId);
    }

    @Override
    public boolean isLocationRestrictionExists(Long theId) {
        return locationRestrictionRepository.existsById(theId);
    }


    // Location Type Implementation

    @Override
    public List<LocationType> findAllLocationType() {
        return locationTypeRepository.findAll();
    }

    @Override
    public LocationType findByLocationTypeId(Long theId) {
        return locationTypeRepository.findById(theId).
            orElseThrow(() -> new ResourceNotFoundException("LocationType", "id", theId));
    }

    @Override
    public LocationType saveLocationType(LocationType theLocationType) {
        if (theLocationType.getId() == null){
            if (locationTypeRepository.findByName(theLocationType.getName()).isPresent()){
                throw new DuplicateValueFoundException("LocationType", "name", theLocationType.getName());
            }
        }else{
            if (locationTypeRepository.findByNameAndIdNot(theLocationType.getName()
                    ,theLocationType.getId()).isPresent()){
                throw new DuplicateValueFoundException("LocationType", "name", theLocationType.getName());
            }
        }
       
        return locationTypeRepository.save(theLocationType);
    }

    @Override
    public void deleteByLocationTypeId(Long theId) {
        locationTypeRepository.deleteById(theId);
    }


    // Location Hierarchy Implementation


    @Override
    public String validateLocationHierarchyRequest(LocationHierarchy theLocationHierarchy) {
        
        Long warehouseId =  theLocationHierarchy.getWarehouseId();
        warehouseService.findById(warehouseId);
        findByLocationTypeId(theLocationHierarchy.getLocationTypeId());

        if (theLocationHierarchy.getId() == null){
            if (locationHierarchyRepository.findByNameAndWarehouseIdAndLocationTypeId(theLocationHierarchy.getName(),
                    warehouseId,theLocationHierarchy.getLocationTypeId()).isPresent()){
                return "Duplicate value found for Location Hierarchy with name:"+theLocationHierarchy.getName();
            }

            if (locationHierarchyRepository.findByCodeAndWarehouseIdAndLocationTypeId(theLocationHierarchy.getCode()
                    ,warehouseId,theLocationHierarchy.getLocationTypeId()).isPresent()){
                return "Duplicate value found for Location Hierarchy with code:"+theLocationHierarchy.getCode();
            }
        }else{
            if (locationHierarchyRepository.findByNameAndWarehouseIdAndLocationTypeIdAndIdNot(theLocationHierarchy.getName()
                    ,warehouseId,theLocationHierarchy.getLocationTypeId(),theLocationHierarchy.getId()).isPresent()){

                return "Duplicate value found for Location Hierarchy Id:"+theLocationHierarchy.getId()
                            +" with name: "+theLocationHierarchy.getName();
            }
            if (locationHierarchyRepository.findByCodeAndWarehouseIdAndLocationTypeIdAndIdNot(theLocationHierarchy.getCode()
                    ,warehouseId,theLocationHierarchy.getLocationTypeId(),theLocationHierarchy.getId()).isPresent()){

                return "Duplicate value found for Location Hierarchy Id:"+theLocationHierarchy.getId()
                        +" with code: "+theLocationHierarchy.getCode();
            }
        }

        if (theLocationHierarchy.getUpperHierarchyId() != null) {
            LocationHierarchy upperHierarchy = findByLocationHierarchyId(theLocationHierarchy.getUpperHierarchyId());
            if (theLocationHierarchy.getLocationTypeId() != upperHierarchy.getLocationTypeId()){
                if (theLocationHierarchy.getId() == null){
                    return "Upper Hierarchy location type must be match with selected location type in Location hierarchy";
                }else{
                    return "Upper Hierarchy location type must be match with selected location type in Location hierarchy Id:"
                            +theLocationHierarchy.getId();
                }
            }
        }

        return null;
    }

    @Override
    public LocationHierarchy saveLocationHierarchy(LocationHierarchy theLocationHierarchy) {
        
        Long warehouseId =  theLocationHierarchy.getWarehouseId();
        Warehouse warehouseObj = warehouseService.findById(warehouseId);
        LocationType locationType = findByLocationTypeId(theLocationHierarchy.getLocationTypeId());

        theLocationHierarchy.setLocationType(locationType);
        theLocationHierarchy.setWarehouse(warehouseObj);
        
        if (theLocationHierarchy.getUpperHierarchyId() != null) {
            LocationHierarchy upperHierarchy = findByLocationHierarchyId(theLocationHierarchy.getUpperHierarchyId());
            theLocationHierarchy.setUpperHierarchy(upperHierarchy);
        }
       
        return locationHierarchyRepository.save(theLocationHierarchy);
    }

    @Override
    public LocationHierarchy findByLocationHierarchyId(Long theId) {
        return locationHierarchyRepository.findById(theId).
            orElseThrow(() -> new ResourceNotFoundException("LocationHierarchy", "id", theId));
    }

    @Override
    public void deleteByLocationHierarchyId(Long theId) {
        locationHierarchyRepository.deleteById(theId);
    }

    @Override
    public Page<LocationHierarchy> findAllLocationHierarchyList(PageHelper page,
                        LocationHierarchyRequest request) {
        return locationHierarchSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public List<LocationHierarchy> getListOfUpperHierarchyByLocationType(LocationType locationType) {
        return locationHierarchyRepository.findByLocationType(locationType);
    }

    // Location Storage Category Implementation


    @Override
    public Page<LocationStorageCategory> findAllLocationStorageCategoryList(PageHelper page,
                        LocationStorageCategoryRequest request) {
        return locationStorageCategorySearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public LocationStorageCategory findByLocationStorageCategoryId(Long theId) {
        return locationStorageCategoryRepository.findById(theId).
            orElseThrow(() -> new ResourceNotFoundException("LocationStorageCategory", "id", theId));
    }

    @Override
    public String validateLocationStorageCategoryRequest(LocationStorageCategory theLocationStorageCategory) {

        Warehouse warehouseObj =  warehouseService.findById(theLocationStorageCategory.getWarehouseId());

        if (theLocationStorageCategory.getId() == null){
            if (locationStorageCategoryRepository.findByNameAndWarehouseId(
                theLocationStorageCategory.getName(),theLocationStorageCategory.getWarehouseId()).isPresent()){
                return "Duplicate value found for Location Storage Category with name:"+theLocationStorageCategory.getName()+" and warehouse:"+warehouseObj.getName();
            }
            if (locationStorageCategoryRepository.findByCodeAndWarehouseId(theLocationStorageCategory.getCode()
                    ,theLocationStorageCategory.getWarehouseId()).isPresent()){
                return "Duplicate value found for Location Storage Category with code:"+theLocationStorageCategory.getCode()+" and warehouse:"+warehouseObj.getName();
            }
        }else{
            if (locationStorageCategoryRepository.findByNameAndWarehouseIdAndIdNot(theLocationStorageCategory.getName(),
                    theLocationStorageCategory.getWarehouseId(),theLocationStorageCategory.getId()).isPresent()){
                return "Duplicate value found for Location Storage Category with Id:"+theLocationStorageCategory.getId()+" and name:"+theLocationStorageCategory.getName()+" and warehouse:"+warehouseObj.getName();
            }
            if (locationStorageCategoryRepository.findByCodeAndWarehouseIdAndIdNot(theLocationStorageCategory.getCode(),
                    theLocationStorageCategory.getWarehouseId(),theLocationStorageCategory.getId()).isPresent()){
                        return "Duplicate value found for Location Storage Category with Id:"+theLocationStorageCategory.getId()+" and code:"+theLocationStorageCategory.getCode()+" and warehouse:"+warehouseObj.getName();
            }
        }
        return null;
    }

    @Override
    public LocationStorageCategory saveLocationStorageCategory(LocationStorageCategory theLocationStorageCategory) {
        Warehouse warehouseObj =  warehouseService.findById(theLocationStorageCategory.getWarehouseId());
        theLocationStorageCategory.setWarehouse(warehouseObj);
        return locationStorageCategoryRepository.save(theLocationStorageCategory);
    }

    @Override
    public void deleteByLocationStorageCategoryId(Long theId) {
        locationStorageCategoryRepository.deleteById(theId);
    }

    // Location Api Implementation

    @Override
    public String validateLocationRequest(Location theLocation) {
        
        if(Objects.nonNull(theLocation.getWarehouseId())){
            Warehouse warehouse =  warehouseService.findById(theLocation.getWarehouseId());
            theLocation.setWarehouse(warehouse);
        }
        
        if(Objects.nonNull(theLocation.getDimensionDefinitionId())){
            DimensionDefinition dimensionDefinition = dimensionDefinitionRepository.findById(theLocation.getDimensionDefinitionId()).
                        orElseThrow(() -> new ResourceNotFoundException("Dimension Definition", 
                        "id", theLocation.getDimensionDefinitionId()));
            theLocation.setDimensionDefinition(dimensionDefinition);
        }
        
        if(Objects.nonNull(theLocation.getLocationRestrictionId())){
            LocationRestriction locationRestrictionObj =  locationRestrictionRepository.findById(theLocation.getLocationRestrictionId()).
                            orElseThrow(() -> new ResourceNotFoundException("Location Restriction", 
                             "id", theLocation.getLocationRestrictionId()));
            theLocation.setLocationRestriction(locationRestrictionObj);
        }
        
        if(Objects.nonNull(theLocation.getLocationStorageCategoryId())){
            LocationStorageCategory locationStorageCategory = locationStorageCategoryRepository.findById(theLocation.getLocationStorageCategoryId()).
                            orElseThrow(() -> new ResourceNotFoundException("Location Storage Category", 
                            "id", theLocation.getLocationStorageCategoryId()));
            theLocation.setLocationStorageCategory(locationStorageCategory);
        }

        if(Objects.nonNull(theLocation.getPrimaryCompanyId())){
            PrimaryCompany primarycompany =  primaryCompanyRepository.findById(theLocation.getPrimaryCompanyId()).
                            orElseThrow(() -> new ResourceNotFoundException("Primary Company", 
                            "id", theLocation.getPrimaryCompanyId()));
            theLocation.setPrimarycompany(primarycompany);
        }

        if(Objects.nonNull(theLocation.getUpperLocationId())){
            Location locationObj = locationRepository.findById(theLocation.getUpperLocationId()).
                            orElseThrow(() -> new ResourceNotFoundException("Location", 
                            "id", theLocation.getUpperLocationId()));
            theLocation.setLocation(locationObj);
        }


        LocationHierarchy locationHierarchy = locationHierarchyRepository.findById(theLocation.getLocationHierarchyId()).
                    orElseThrow(() -> new ResourceNotFoundException("LocationHierarchy", 
                    "id", theLocation.getLocationHierarchyId()));
        
        theLocation.setLocationHierarchy(locationHierarchy);

        if (theLocation.getId() == null){
            if (locationRepository.findByCodeAndLocationHierarchyId(theLocation.getCode()
                    ,theLocation.getLocationHierarchyId()).isPresent()){
                return "Duplicate value found for Location with code:"+theLocation.getCode()+" and Locaiton Hierarchy:"+locationHierarchy.getCode();
            }
        }else{
            if (locationRepository.findByCodeAndLocationHierarchyIdAndIdNot(theLocation.getCode()
                    ,theLocation.getLocationHierarchyId(),theLocation.getId()).isPresent()){
                return "Duplicate value found for Location with Id:"+theLocation.getId()+" and code"+theLocation.getCode()+" and Locaiton Hierarchy:"+locationHierarchy.getCode();
            }
        }

        return null;
    }

    @Override
    public Location saveLocation(Location theLocation) {
        return locationRepository.save(theLocation);
    }

    @Override
    public Location findByLocationId(Long theId) {
        return locationRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Location","id", theId));
    }

    @Override
    public Page<Location> findAllLocationList(PageHelper page, LocationRequest request) {
        return locationSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByLocationId(Long theId) {
        locationRepository.deleteById(theId);
    }
    
}

