package com.warehouse.enums;

public enum TradeTermEnum {

    Select(""),ExWarehouse("1"), LocalDelivery("2");
    private String shortName;

    private TradeTermEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static TradeTermEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return TradeTermEnum.ExWarehouse;
            case "2":
                return TradeTermEnum.LocalDelivery;
            default:
                return TradeTermEnum.Select;
        }
    }

}
