package com.warehouse.enums;

public enum UomWeightEnum {

    Select(""), gm("1"), kg("2"), lg("3"), mg("4"), oz("5"), t("6");

    private final String shortName;

    UomWeightEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static UomWeightEnum fromShortName(String shortName){
        switch (shortName){
            case "1":
                return UomWeightEnum.gm;
            case "2":
                return UomWeightEnum.kg;
            case "3":
                return UomWeightEnum.lg;
            case "4":
                return UomWeightEnum.mg;
            case "5":
                return UomWeightEnum.oz;
            case "6":
                return UomWeightEnum.t;
            default:
                return UomWeightEnum.Select;
        }
    }
}
