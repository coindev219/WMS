package com.warehouse.enums;

public enum UomLengthEnum {

    Select(""), cm("1"), m("2"), mm("3");

    private final String shortName;

    UomLengthEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static UomLengthEnum fromShortName(String shortName){
        switch (shortName){
            case "1":
                return UomLengthEnum.cm;
            case "2":
                return UomLengthEnum.m;
            case "3":
                return UomLengthEnum.mm;
            default:
                return UomLengthEnum.Select;
        }
    }
}
