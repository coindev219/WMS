package com.warehouse.setup.location;

public class LocationRequest {

    private Long id;

    private String code;

    private Long locationHierarchyId;

    private Long warehouseId;

    private Long primaryCompanyId;

    private Long dimensionDefinitionId;

    private Long locationRestrictionId;

    private Long locationStorageCategoryId;

    private String outerLocation;

    private Long upperLocationId;

    private String usable;

    private String pickFace;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String optionE;

    private String restrictSuggestion;

    private String restrictAllocation;

    private String isPackLocation;

    private Integer binningSequenceNo;

    private Integer pickingSequenceNo;

    private String locationGroup;

    private String searchFor;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getModifiedFrom() {
        return modifiedFrom;
    }

    public void setModifiedFrom(String modifiedFrom) {
        this.modifiedFrom = modifiedFrom;
    }

    public String getModifiedTo() {
        return modifiedTo;
    }

    public void setModifiedTo(String modifiedTo) {
        this.modifiedTo = modifiedTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLocationHierarchyId() {
        return locationHierarchyId;
    }

    public void setLocationHierarchyId(Long locationHierarchyId) {
        this.locationHierarchyId = locationHierarchyId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getDimensionDefinitionId() {
        return dimensionDefinitionId;
    }

    public void setDimensionDefinitionId(Long dimensionDefinitionId) {
        this.dimensionDefinitionId = dimensionDefinitionId;
    }

    public Long getLocationRestrictionId() {
        return locationRestrictionId;
    }

    public void setLocationRestrictionId(Long locationRestrictionId) {
        this.locationRestrictionId = locationRestrictionId;
    }

    public Long getLocationStorageCategoryId() {
        return locationStorageCategoryId;
    }

    public void setLocationStorageCategoryId(Long locationStorageCategoryId) {
        this.locationStorageCategoryId = locationStorageCategoryId;
    }

    public String getOuterLocation() {
        return outerLocation;
    }

    public void setOuterLocation(String outerLocation) {
        this.outerLocation = outerLocation;
    }

    public Long getUpperLocationId() {
        return upperLocationId;
    }

    public void setUpperLocationId(Long upperLocationId) {
        this.upperLocationId = upperLocationId;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public String getPickFace() {
        return pickFace;
    }

    public void setPickFace(String pickFace) {
        this.pickFace = pickFace;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getRestrictSuggestion() {
        return restrictSuggestion;
    }

    public void setRestrictSuggestion(String restrictSuggestion) {
        this.restrictSuggestion = restrictSuggestion;
    }

    public String getRestrictAllocation() {
        return restrictAllocation;
    }

    public void setRestrictAllocation(String restrictAllocation) {
        this.restrictAllocation = restrictAllocation;
    }

    public String getIsPackLocation() {
        return isPackLocation;
    }

    public void setIsPackLocation(String isPackLocation) {
        this.isPackLocation = isPackLocation;
    }

    public Integer getBinningSequenceNo() {
        return binningSequenceNo;
    }

    public void setBinningSequenceNo(Integer binningSequenceNo) {
        this.binningSequenceNo = binningSequenceNo;
    }

    public Integer getPickingSequenceNo() {
        return pickingSequenceNo;
    }

    public void setPickingSequenceNo(Integer pickingSequenceNo) {
        this.pickingSequenceNo = pickingSequenceNo;
    }

    public String getLocationGroup() {
        return locationGroup;
    }

    public void setLocationGroup(String locationGroup) {
        this.locationGroup = locationGroup;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    @Override
    public String toString() {
        return "LocationRequest [binningSequenceNo=" + binningSequenceNo + ", code=" + code + ", createdFrom="
                + createdFrom + ", createdTo=" + createdTo + ", dimensionDefinitionId=" + dimensionDefinitionId
                + ", id=" + id + ", isPackLocation=" + isPackLocation + ", locationGroup=" + locationGroup
                + ", locationHierarchyId=" + locationHierarchyId + ", locationRestrictionId=" + locationRestrictionId
                + ", locationStorageCategoryId=" + locationStorageCategoryId + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC="
                + optionC + ", optionD=" + optionD + ", optionE=" + optionE + ", outerLocation=" + outerLocation
                + ", pickFace=" + pickFace + ", pickingSequenceNo=" + pickingSequenceNo + ", primaryCompanyId="
                + primaryCompanyId + ", restrictAllocation=" + restrictAllocation + ", restrictSuggestion="
                + restrictSuggestion + ", searchFor=" + searchFor + ", upperLocationId=" + upperLocationId + ", usable="
                + usable + ", warehouseId=" + warehouseId + "]";
    }

    

}
