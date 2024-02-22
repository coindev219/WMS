package com.warehouse.enums;

public enum PrinterOperationTypeEnum {

    Select(""), AdHoc("1"), Inbound("2"), InStock("3"), IST("4"), OutBound("5");

    private String shortName;
 
    private PrinterOperationTypeEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static PrinterOperationTypeEnum fromShortName(String shortName) {
        switch (shortName) {
        case "1":
            return PrinterOperationTypeEnum.AdHoc;
        case "2":
            return PrinterOperationTypeEnum.Inbound;
        case "3":
            return PrinterOperationTypeEnum.InStock;
        case "4":
            return PrinterOperationTypeEnum.IST;
        case "5":
            return PrinterOperationTypeEnum.OutBound;
        default:
            return PrinterOperationTypeEnum.Select;
        }
    }

}