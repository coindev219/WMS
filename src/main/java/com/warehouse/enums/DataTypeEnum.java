package com.warehouse.enums;

public enum DataTypeEnum {

    Select(""), AlphaNumeric("1") , Character("2"), Numeric("3");

    private String shortName;
 
    private DataTypeEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static DataTypeEnum fromShortName(String shortName) {
        switch (shortName) {
        case "1":
            return DataTypeEnum.AlphaNumeric;
        case "2":
            return DataTypeEnum.Character;
        case "3":
            return DataTypeEnum.Numeric;
        default:
            return DataTypeEnum.Select;
        }
    }

}