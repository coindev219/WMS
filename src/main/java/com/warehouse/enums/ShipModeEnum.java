package com.warehouse.enums;

public enum ShipModeEnum {

    Select(""),CarriedByAir("1"), CarriedByHand("2"),CarriedByRoad("3"), CarriedBySea("4");
    private String shortName;

    private ShipModeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static ShipModeEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return ShipModeEnum.CarriedByAir;
            case "2":
                return ShipModeEnum.CarriedByHand;
            case "3":
                return ShipModeEnum.CarriedByRoad;
            case "4":
                return ShipModeEnum.CarriedBySea;
            default:
                return ShipModeEnum.Select;
        }
    }

}
