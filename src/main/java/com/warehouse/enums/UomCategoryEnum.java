package com.warehouse.enums;

public enum UomCategoryEnum {

    Select(""),
    AreaUOM("1"),
    NumberUOM("2"),
    SKUsAndPacksLengthUOM("3"),
    SKUsAndPacksWeightUOM("4"),
    TimeUOM("5"),
    VolumeUOM("6");

    private final String shortName;

    UomCategoryEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static UomCategoryEnum fromShortName(String shortName) {

        switch (shortName) {
            case "1":
                return UomCategoryEnum.AreaUOM;
            case "2":
                return UomCategoryEnum.NumberUOM;
            case "3":
                return UomCategoryEnum.SKUsAndPacksLengthUOM;
            case "4":
                return UomCategoryEnum.SKUsAndPacksWeightUOM;
            case "5":
                return UomCategoryEnum.TimeUOM;
            case "6":
                return UomCategoryEnum.VolumeUOM;
            default:
                return UomCategoryEnum.Select;
        }
    }

}
