package com.warehouse.enums;

public enum WeekDayEnum {

    Select(""),Sunday("1"), Monday("2"),Tuesday("3"), Wednesday("4"), Thursday("5"), Friday("6"),Saturday("7");

    private String shortName;
 
    private WeekDayEnum(String shortName) {
        this.shortName = shortName;
    }
 
    public String getShortName() {
        return shortName;
    }
 
    public static WeekDayEnum fromShortName(String shortName) {
        switch (shortName) {
        case "0":
            return WeekDayEnum.Sunday;
        case "1":
            return WeekDayEnum.Monday;
        case "2":
            return WeekDayEnum.Tuesday;
        case "3":
            return WeekDayEnum.Wednesday;
        case "4":
            return WeekDayEnum.Thursday;
        case "5":
            return WeekDayEnum.Friday;
        case "6":
            return WeekDayEnum.Saturday;
        default:
            return WeekDayEnum.Select;
        }
    }

}