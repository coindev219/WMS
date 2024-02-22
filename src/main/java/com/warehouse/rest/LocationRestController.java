package com.warehouse.rest;

import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.location.Location;
import com.warehouse.setup.location.LocationListAction;
import com.warehouse.setup.location.LocationRequest;
import com.warehouse.setup.location.LocationService;
import com.warehouse.setup.location.dimension.DimensionDefinition;
import com.warehouse.setup.location.dimension.DimensionDefinitionListAction;
import com.warehouse.setup.location.dimension.DimensionDefinitionRequest;
import com.warehouse.setup.location.hierarchy.LocationHierarchy;
import com.warehouse.setup.location.hierarchy.LocationHierarchyListAction;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRequest;
import com.warehouse.setup.location.locationtype.LocationType;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.restriction.LocationRestrictionListAction;
import com.warehouse.setup.location.restriction.LocationRestrictionRequest;
import com.warehouse.setup.location.storagecategory.LocationStorageCategory;
import com.warehouse.setup.location.storagecategory.LocationStorageCategoryListAction;
import com.warehouse.setup.location.storagecategory.LocationStorageCategoryRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup/location")
public class LocationRestController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/dimension-definition/search")
    public ResponseEntity<Page<DimensionDefinition>> getDimensionDefinitionList(PageHelper page,
            @RequestBody DimensionDefinitionRequest request) {
        return new ResponseEntity<>(locationService.findAllDimensionDefinitionList(page, request), HttpStatus.OK);
    }

    @GetMapping("/dimension-definition/{id}")
    public DimensionDefinition getDimensionDefinitionById(@PathVariable(value = "id") Long theId) {
        return locationService.findByDimensionDefinitionId(theId);
    }

    @PostMapping("/dimension-definition")
    public DimensionDefinition addDimensionDefinition(@Valid @RequestBody DimensionDefinition theDimensionDefinition) {

        User user = WarehouseHelper.getLoggedInUser();
        theDimensionDefinition.setCreatedBy(user);
        theDimensionDefinition.setModifiedBy(user);

        theDimensionDefinition = locationService.saveDimensionDefinition(theDimensionDefinition);
        return theDimensionDefinition;
    }

    @PutMapping("/dimension-definition/{id}")
    public DimensionDefinition updateDimensionDefinition(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody DimensionDefinition theDimensionDefinition) {

        User user = WarehouseHelper.getLoggedInUser();

        theDimensionDefinition.setModifiedBy(user);

        theDimensionDefinition.setId(theId);
        DimensionDefinition dimensionDefinitionObj = locationService.findByDimensionDefinitionId(theId);
        theDimensionDefinition.setCreatedAt(dimensionDefinitionObj.getCreatedAt());
        theDimensionDefinition.setCreatedBy(dimensionDefinitionObj.getCreatedBy());

        dimensionDefinitionObj = locationService.saveDimensionDefinition(theDimensionDefinition);
        return dimensionDefinitionObj;
    }

    @PutMapping("/dimension-definition")
    public DimensionDefinitionListAction updateDimensionDefinitionList(
            @Valid @RequestBody DimensionDefinitionListAction listAction) {
        DimensionDefinitionListAction responseListAction = new DimensionDefinitionListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (DimensionDefinition singleObj : listAction) {
            DimensionDefinition searchedObj = locationService.findByDimensionDefinitionId(singleObj.getId());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            responseListAction.add(locationService.saveDimensionDefinition(singleObj));
        }

        return responseListAction;
    }

    @DeleteMapping("/dimension-definition/{id}")
    public ResponseEntity<?> deleteDimensionDefinition(@PathVariable(value = "id") Long theId) {
        locationService.findByDimensionDefinitionId(theId);
        locationService.deleteByDimensionDefinitionId(theId);
        return ResponseEntity.ok().build();
    }

    // Location Restriction Api

    @PostMapping("/restriction/search")
    public ResponseEntity<Page<LocationRestriction>> getLocationRestrictionList(PageHelper page,
            @RequestBody LocationRestrictionRequest request) {
        return new ResponseEntity<>(locationService.findAllLocationRestrictionList(page, request), HttpStatus.OK);
    }

    @GetMapping("/restriction/{id}")
    public LocationRestriction getLocationRestrictionById(@PathVariable(value = "id") Long theId) {
        return locationService.findByLocationRestrictionId(theId);
    }

    @PostMapping("/restriction")
    public LocationRestriction addLocationRestriction(@Valid @RequestBody LocationRestriction theLocationRestriction) {

        User user = WarehouseHelper.getLoggedInUser();
        theLocationRestriction.setCreatedBy(user);
        theLocationRestriction.setModifiedBy(user);

        theLocationRestriction = locationService.saveLocationRestriction(theLocationRestriction);
        return theLocationRestriction;
    }

    @PutMapping("/restriction/{id}")
    public LocationRestriction updateLocationRestriction(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody LocationRestriction theLocationRestriction) {

        User user = WarehouseHelper.getLoggedInUser();

        theLocationRestriction.setId(theId);
        LocationRestriction locationRestrictionObj = locationService.findByLocationRestrictionId(theId);
        theLocationRestriction.setCreatedBy(locationRestrictionObj.getCreatedBy());
        theLocationRestriction.setModifiedBy(user);

        theLocationRestriction.setCreatedAt(locationRestrictionObj.getCreatedAt());
        locationRestrictionObj = locationService.saveLocationRestriction(theLocationRestriction);
        return locationRestrictionObj;
    }

    @PutMapping("/restriction")
    public LocationRestrictionListAction updateLocationRestrictionList(
            @Valid @RequestBody LocationRestrictionListAction listAction) {

        User user = WarehouseHelper.getLoggedInUser();

        LocationRestrictionListAction responseListAction = new LocationRestrictionListAction();
        for (LocationRestriction singleObj : listAction) {

            LocationRestriction searchedObj = locationService.findByLocationRestrictionId(singleObj.getId());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            responseListAction.add(locationService.saveLocationRestriction(singleObj));
        }

        return responseListAction;
    }

    @DeleteMapping("/restriction/{id}")
    public ResponseEntity<?> deleteLocationRestriction(@PathVariable(value = "id") Long theId) {
        locationService.findByLocationRestrictionId(theId);
        locationService.deleteByLocationRestrictionId(theId);
        return ResponseEntity.ok().build();
    }

    // Location Type api

    @GetMapping("/location-type")
    public List<LocationType> getAllLocationType() {
        return locationService.findAllLocationType();
    }

    @GetMapping("/location-type/{id}")
    public LocationType getLocationTypeById(@PathVariable(value = "id") Long theId) {
        return locationService.findByLocationTypeId(theId);
    }

    @PostMapping("/location-type")
    public LocationType addLocationType(@Valid @RequestBody LocationType theLocationType) {

        User user = WarehouseHelper.getLoggedInUser();
        theLocationType.setCreatedBy(user);
        theLocationType.setModifiedBy(user);

        locationService.saveLocationType(theLocationType);
        return theLocationType;
    }

    @PutMapping("/location-type/{id}")
    public LocationType updateLocationType(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody LocationType theLocationType) {
        LocationType singleObj = locationService.findByLocationTypeId(theId);
        singleObj.setName(theLocationType.getName());

        User user = WarehouseHelper.getLoggedInUser();
        theLocationType.setCreatedBy(singleObj.getCreatedBy());
        theLocationType.setModifiedBy(user);

        singleObj = locationService.saveLocationType(singleObj);
        return singleObj;
    }

    @DeleteMapping("/location-type/{id}")
    public ResponseEntity<?> deleteLocationType(@PathVariable(value = "id") Long theId) {
        locationService.findByLocationTypeId(theId);
        locationService.deleteByLocationTypeId(theId);
        return ResponseEntity.ok().build();
    }

    // Location Hierarchy Api

    @PostMapping("/hierarchy")
    public LocationHierarchy addLocationHierarchy(@Valid @RequestBody LocationHierarchy theLocationHierarchy) {
        String error = locationService.validateLocationHierarchyRequest(theLocationHierarchy);
        if (error != null) {
            throw new InvalidValueFoundException("LocationHierarchy", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocationHierarchy.setCreatedBy(user);
        theLocationHierarchy.setModifiedBy(user);

        locationService.saveLocationHierarchy(theLocationHierarchy);
        return theLocationHierarchy;
    }

    @GetMapping("/hierarchy/{id}")
    public LocationHierarchy getLocationHierarchyById(@PathVariable(value = "id") Long theId) {
        return locationService.findByLocationHierarchyId(theId);
    }

    @PutMapping("/hierarchy/{id}")
    public LocationHierarchy updateLocationHierarchy(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody LocationHierarchy theLocationHierarchy) {
        theLocationHierarchy.setId(theId);
        LocationHierarchy locationHierarchyObj = locationService.findByLocationHierarchyId(theId);
        theLocationHierarchy.setCreatedAt(locationHierarchyObj.getCreatedAt());

        String error = locationService.validateLocationHierarchyRequest(theLocationHierarchy);
        if (error != null) {
            throw new InvalidValueFoundException("LocationHierarchy", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocationHierarchy.setCreatedBy(locationHierarchyObj.getCreatedBy());
        theLocationHierarchy.setModifiedBy(user);

        locationHierarchyObj = locationService.saveLocationHierarchy(theLocationHierarchy);
        return locationHierarchyObj;
    }

    @PostMapping("/hierarchy/search")
    public ResponseEntity<Page<LocationHierarchy>> getLocationHierarchyList(PageHelper page,
            @RequestBody LocationHierarchyRequest request) {
        return new ResponseEntity<>(locationService.findAllLocationHierarchyList(page, request), HttpStatus.OK);
    }

    @PutMapping("/hierarchy")
    public LocationHierarchyListAction updateLocationHierarchyList(
            @Valid @RequestBody LocationHierarchyListAction listAction) {

        LocationHierarchyListAction responseListAction = new LocationHierarchyListAction();
        for (LocationHierarchy singleObj : listAction) {
            LocationHierarchy searchedObj = locationService.findByLocationHierarchyId(singleObj.getId());
            String error = locationService.validateLocationHierarchyRequest(searchedObj);
            if (error != null) {
                throw new InvalidValueFoundException("LocationHierarchy", error);
            }
            singleObj.setCreatedAt(searchedObj.getCreatedAt());

            User user = WarehouseHelper.getLoggedInUser();
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(locationService.saveLocationHierarchy(singleObj));
        }

        return responseListAction;
    }

    @DeleteMapping("/hierarchy/{id}")
    public ResponseEntity<?> deleteLocationHierarchy(@PathVariable(value = "id") Long theId) {
        locationService.findByLocationHierarchyId(theId);
        locationService.deleteByLocationHierarchyId(theId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hierarchy/locationType/{locationTypeId}/upperHierarchy")
    public List<LocationHierarchy> getListOfUpperHierarchy(
            @PathVariable(value = "locationTypeId") Long locationTypeId) {
        LocationType locationType = locationService.findByLocationTypeId(locationTypeId);
        return locationService.getListOfUpperHierarchyByLocationType(locationType);
    }

    // Location Storage Category Api

    @PostMapping("/storage-category/search")
    public ResponseEntity<Page<LocationStorageCategory>> getLocationStorageCategoryList(PageHelper page,
            @RequestBody LocationStorageCategoryRequest request) {
        return new ResponseEntity<>(locationService.findAllLocationStorageCategoryList(page, request), HttpStatus.OK);
    }

    @GetMapping("/storage-category/{id}")
    public LocationStorageCategory getLocationStorageCategoryById(@PathVariable(value = "id") Long theId) {
        return locationService.findByLocationStorageCategoryId(theId);
    }

    @PostMapping("/storage-category")
    public LocationStorageCategory addLocationStorageCategory(
            @Valid @RequestBody LocationStorageCategory theLocationStorageCategory) {

        String error = locationService.validateLocationStorageCategoryRequest(theLocationStorageCategory);
        if (error != null) {
            throw new InvalidValueFoundException("LocationStorageCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocationStorageCategory.setCreatedBy(user);
        theLocationStorageCategory.setModifiedBy(user);

        theLocationStorageCategory = locationService.saveLocationStorageCategory(theLocationStorageCategory);
        return theLocationStorageCategory;
    }

    @PutMapping("/storage-category/{id}")
    public LocationStorageCategory updateLocationStorageCategory(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody LocationStorageCategory theLocationStorageCategory) {
        theLocationStorageCategory.setId(theId);
        LocationStorageCategory locationStorageCategoryObj = locationService.findByLocationStorageCategoryId(theId);

        String error = locationService.validateLocationStorageCategoryRequest(theLocationStorageCategory);
        if (error != null) {
            throw new InvalidValueFoundException("LocationStorageCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocationStorageCategory.setCreatedBy(locationStorageCategoryObj.getCreatedBy());
        theLocationStorageCategory.setModifiedBy(user);

        theLocationStorageCategory.setCreatedAt(locationStorageCategoryObj.getCreatedAt());
        locationStorageCategoryObj = locationService.saveLocationStorageCategory(theLocationStorageCategory);
        return locationStorageCategoryObj;
    }

    @PutMapping("/storage-category")
    public LocationStorageCategoryListAction updateLocationStorageCategoryList(
            @Valid @RequestBody LocationStorageCategoryListAction listAction) {

        User user = WarehouseHelper.getLoggedInUser();

        LocationStorageCategoryListAction responseListAction = new LocationStorageCategoryListAction();
        for (LocationStorageCategory singleObj : listAction) {
            locationService.findByLocationStorageCategoryId(singleObj.getId());
            String error = locationService.validateLocationStorageCategoryRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("LocationStorageCategory", error);
            }
        }
        for (LocationStorageCategory singleObj : listAction) {
            LocationStorageCategory searchedObj = locationService.findByLocationStorageCategoryId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(locationService.saveLocationStorageCategory(singleObj));
        }

        return responseListAction;
    }

    @DeleteMapping("/storage-category/{id}")
    public ResponseEntity<?> deleteLocationStorageCategory(@PathVariable(value = "id") Long theId) {
        locationService.findByLocationStorageCategoryId(theId);
        locationService.deleteByLocationStorageCategoryId(theId);
        return ResponseEntity.ok().build();
    }

    // Location Api Methods

    @PostMapping("/save")
    public Location addLocation(@Valid @RequestBody Location theLocation) {
        String error = locationService.validateLocationRequest(theLocation);
        if (error != null) {
            throw new InvalidValueFoundException("Location", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocation.setCreatedBy(user);
        theLocation.setModifiedBy(user);

        theLocation = locationService.saveLocation(theLocation);
        return theLocation;
    }

    @PutMapping("/update/{id}")
    public Location updateLocation(@PathVariable(value = "id") Long theId, @Valid @RequestBody Location theLocation) {
        theLocation.setId(theId);
        Location locationObj = locationService.findByLocationId(theId);

        String error = locationService.validateLocationRequest(theLocation);
        if (error != null) {
            throw new InvalidValueFoundException("Location", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theLocation.setCreatedBy(locationObj.getCreatedBy());
        theLocation.setModifiedBy(user);

        theLocation.setCreatedAt(locationObj.getCreatedAt());
        locationObj = locationService.saveLocation(theLocation);
        return locationObj;
    }

    @PutMapping("/update")
    public LocationListAction updateLocationList(@Valid @RequestBody LocationListAction listAction) {

        LocationListAction responseListAction = new LocationListAction();
        for (Location singleObj : listAction) {
            locationService.findByLocationId(singleObj.getId());
            String error = locationService.validateLocationRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Location", error);
            }
        }
        for (Location singleObj : listAction) {
            Location searchedObj = locationService.findByLocationId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());

            User user = WarehouseHelper.getLoggedInUser();
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(locationService.saveLocation(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/detail/{id}")
    public Location getLocationById(@PathVariable(value = "id") Long theId) {
        return locationService.findByLocationId(theId);
    }

    @PostMapping("/list/search")
    public ResponseEntity<Page<Location>> getLocationList(PageHelper page, @RequestBody LocationRequest request) {
        return new ResponseEntity<>(locationService.findAllLocationList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable(value = "id") Long theId) {
        locationService.deleteByLocationId(theId);
        return ResponseEntity.ok().build();
    }

}
