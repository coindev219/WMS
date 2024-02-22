package com.warehouse.enums;

public enum CostingMethodEnum {

    Select(""), MovingAverage("1"), SalesPrice("2");

    private final String shortName;

    CostingMethodEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static CostingMethodEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return CostingMethodEnum.MovingAverage;
            case "2":
                return CostingMethodEnum.SalesPrice;
            default:
                return CostingMethodEnum.Select;
        }
    }
}
