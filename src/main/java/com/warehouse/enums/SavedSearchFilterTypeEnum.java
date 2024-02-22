package com.warehouse.enums;

public enum SavedSearchFilterTypeEnum {

    Select(""), WAREHOUSE("Warehouse"), DIMENSION_DEFINATION("DimensionDefinition"),
    LOCATION_RESTRICTION("LocationRestriction"), LOCATION_HIERARCHY("LocationHierarchy"), LOCATION("Location"),
    LOCATION_STORAGE_CATEGORY("LocationStorageCategory"), PRIMARY_COMPANY("PrimaryCompany");

    private String shortName;

    private SavedSearchFilterTypeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static SavedSearchFilterTypeEnum fromShortName(String shortName) {
        switch (shortName) {
            case "Warehouse":
                return SavedSearchFilterTypeEnum.WAREHOUSE;
            case "DimensionDefinition":
                return SavedSearchFilterTypeEnum.DIMENSION_DEFINATION;
            case "LocationRestriction":
                return SavedSearchFilterTypeEnum.LOCATION_RESTRICTION;
            case "LocationHierarchy":
                return SavedSearchFilterTypeEnum.LOCATION_HIERARCHY;
            case "Location":
                return SavedSearchFilterTypeEnum.LOCATION;
            case "LocationStorageCategory":
                return SavedSearchFilterTypeEnum.LOCATION_STORAGE_CATEGORY;
            case "PrimaryCompany":
                return SavedSearchFilterTypeEnum.PRIMARY_COMPANY;
                
            default:
                return SavedSearchFilterTypeEnum.Select;
        }
    }

}
