package com.warehouse.enums;

public enum PrinterLabelTypeEnum {

    Select(""), PackLabel("1"), SKULabel("2"), UserLabel("3");

    private String shortName;
 
    private PrinterLabelTypeEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static PrinterLabelTypeEnum fromShortName(String shortName) {
        switch (shortName) {
        case "1":
            return PrinterLabelTypeEnum.PackLabel;
        case "2":
            return PrinterLabelTypeEnum.SKULabel;
        case "3":
            return PrinterLabelTypeEnum.UserLabel;
        default:
            return PrinterLabelTypeEnum.Select;
        }
    }

}