package com.warehouse.enums;

public enum SearchTypeEnum {

    Select(""), NORMAL("1") , NOTIFICATION("2");

    private String shortName;
 
    private SearchTypeEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static SearchTypeEnum fromShortName(String shortName) {
        switch (shortName) {
        case "1":
            return SearchTypeEnum.NORMAL;
        case "2":
            return SearchTypeEnum.NOTIFICATION;
        default:
            return SearchTypeEnum.Select;
        }
    }

}
