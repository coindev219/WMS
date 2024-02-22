package com.warehouse.enums;

public enum ComplexityEnum {

    Select(""), High("1"), Low("2"), Medium("3"), MediumHigh("4"), MediumLow("5"), VeryHigh("6"), VeryLow("7");

    private final String shortName;

    ComplexityEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static ComplexityEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return ComplexityEnum.High;
            case "2":
                return ComplexityEnum.Low;
            case "3":
                return ComplexityEnum.Medium;
            case "4":
                return ComplexityEnum.MediumHigh;
            case "5":
                return ComplexityEnum.MediumLow;
            case "6":
                return ComplexityEnum.VeryHigh;
            case "7":
                return ComplexityEnum.VeryLow;
            default:
                return ComplexityEnum.Select;
        }
    }
}
