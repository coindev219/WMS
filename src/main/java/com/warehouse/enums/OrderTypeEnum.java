package com.warehouse.enums;



public enum OrderTypeEnum {

    Select(""), PO("1"), SO("2"), ExtStockTrf("3");

    private final String shortName;

    OrderTypeEnum(String shortName){this.shortName = shortName;}

    public String getShortName(){return shortName;}

    public static OrderTypeEnum fromShortName(String shortName){
        switch (shortName){
            case "1":
                return  OrderTypeEnum.PO ;
            case "2":
                return OrderTypeEnum.SO;
            case "3":
                return OrderTypeEnum.ExtStockTrf;
            default:
                return OrderTypeEnum.Select;
        }
    }
}
