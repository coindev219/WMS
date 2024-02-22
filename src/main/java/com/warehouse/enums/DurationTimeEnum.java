package com.warehouse.enums;

public enum DurationTimeEnum {

    Select(""),Days("1"), Weeks("2"),Months("3"), Years("4");
    private String shortName;

    private DurationTimeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static DurationTimeEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return DurationTimeEnum.Days;
            case "2":
                return DurationTimeEnum.Weeks;
            case "3":
                return DurationTimeEnum.Months;
            case "4":
                return DurationTimeEnum.Years;
            default:
                return DurationTimeEnum.Select;
        }
    }

}
