package com.warehouse.enums;

public enum OperationTypeEnum {

    Select(""), In("1"), InOut("2"), Out("3");

    private final String shortName;

    OperationTypeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static OperationTypeEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return OperationTypeEnum.In;
            case "2":
                return OperationTypeEnum.InOut;
            case "3":
                return OperationTypeEnum.Out;
            default:
                return OperationTypeEnum.Select;
        }
    }
}
