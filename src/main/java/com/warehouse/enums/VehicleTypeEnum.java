package com.warehouse.enums;

public enum VehicleTypeEnum {

    Select(""), Pallet("1");

    private final String shortName;

    VehicleTypeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static VehicleTypeEnum fromShortName(String shortName) {

        switch (shortName) {
            case "1":
                return VehicleTypeEnum.Pallet;
            default:
                return VehicleTypeEnum.Select;
        }
    }
}
