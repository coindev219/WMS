package com.warehouse.enums;

public enum UomVolumeEnum {

    Select(""),
    cm3("1"),
    ft3("2"),
    l("3"),
    m3("4"),
    ml("5");

    private final String shortName;

    UomVolumeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static UomVolumeEnum fromShortName(String shortName){
        switch (shortName) {
            case "1":
                return UomVolumeEnum.cm3;
            case "2":
                return UomVolumeEnum.ft3;
            case "3":
                return UomVolumeEnum.l;
            case "4":
                return UomVolumeEnum.m3;
            case "5":
                return UomVolumeEnum.ml;
            default:
                return UomVolumeEnum.Select;
        }
    }
}
