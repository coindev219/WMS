package com.warehouse.setup.location;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.location.dimension.DimensionDefinition;
import com.warehouse.setup.location.dimension.DimensionDefinitionRequest;
import com.warehouse.setup.location.hierarchy.LocationHierarchy;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRequest;
import com.warehouse.setup.location.locationtype.LocationType;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.restriction.LocationRestrictionRequest;
import com.warehouse.setup.location.storagecategory.LocationStorageCategory;
import com.warehouse.setup.location.storagecategory.LocationStorageCategoryRequest;

import org.springframework.data.domain.Page;

public interface LocationService {
	
	// Dimension Definition Api 

    public Page<DimensionDefinition> findAllDimensionDefinitionList(PageHelper page,
				DimensionDefinitionRequest request);
	
	public DimensionDefinition findByDimensionDefinitionId(Long theId);
	
	public DimensionDefinition saveDimensionDefinition(DimensionDefinition theDimensionDefinition);
	
	public void deleteByDimensionDefinitionId(Long theId);

	public boolean isDimensionDefinitionExists(Long theId);


	// Location Restriction Api 
	public Page<LocationRestriction> findAllLocationRestrictionList(PageHelper page,
					LocationRestrictionRequest request);

	public LocationRestriction findByLocationRestrictionId(Long theId);

	public LocationRestriction saveLocationRestriction(LocationRestriction theDimensionDefinition);

	public void deleteByLocationRestrictionId(Long theId);

	public boolean isLocationRestrictionExists(Long theId);


	// Location Type Api

	public List<LocationType> findAllLocationType();
	
	public LocationType findByLocationTypeId(Long theId);
	
	public LocationType saveLocationType(LocationType theLocationType);
	
	public void deleteByLocationTypeId(Long theId);


	//Location Hierarchy Api

	public Page<LocationHierarchy> findAllLocationHierarchyList(PageHelper page,
	 				LocationHierarchyRequest request);

	public LocationHierarchy findByLocationHierarchyId(Long theId);

	public String validateLocationHierarchyRequest(LocationHierarchy theLocationHierarchy);
	
	public LocationHierarchy saveLocationHierarchy(LocationHierarchy theLocationHierarchy);

	public void deleteByLocationHierarchyId(Long theId);

	public List<LocationHierarchy> getListOfUpperHierarchyByLocationType(LocationType locationType);
	

	// Location Category api
	public Page<LocationStorageCategory> findAllLocationStorageCategoryList(PageHelper page,
						LocationStorageCategoryRequest request);

	public String validateLocationStorageCategoryRequest(LocationStorageCategory theLocationCategory);

	public LocationStorageCategory findByLocationStorageCategoryId(Long theId);

	public LocationStorageCategory saveLocationStorageCategory(LocationStorageCategory theDimensionDefinition);

	public void deleteByLocationStorageCategoryId(Long theId);



	//Location Api 
	public String validateLocationRequest(Location theLocation);
    public Location saveLocation(Location theLocation);
    public Location findByLocationId(Long theId);
    public Page<Location> findAllLocationList(PageHelper page,LocationRequest request);
    public void deleteByLocationId(Long theId);










	
	
	
}
