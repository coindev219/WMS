package com.warehouse.enums;

public enum AllocRuleEnum {

    Select(""), FEFO_AND_LocationFlush("1"), FEFO_FIFO_AND_LocationFlush("2"), FEFO_LIFO_AND_LocationFlush("3"),
    FIFO_AND_LocationFlush("4"), FMFO_AND_LocationFlush("5"), FMFO_FIFO_AND_LocationFlush("6"),
    FMFO_LIFO_AND_LocationFlush("7"), LIFO_AND_LocationFlush("8"), LocationFlush("9"),
    SalesOrderingUOMLocationFlush("10"), WeightBasedAllocation("11");

    private final String shortName;

    AllocRuleEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static AllocRuleEnum fromShortName(String shortName) {

        switch (shortName) {
            case "1":
                return AllocRuleEnum.FEFO_AND_LocationFlush;
            case "2":
                return AllocRuleEnum.FEFO_FIFO_AND_LocationFlush;
            case "3":
                return AllocRuleEnum.FEFO_LIFO_AND_LocationFlush;
            case "4":
                return AllocRuleEnum.FIFO_AND_LocationFlush;
            case "5":
                return AllocRuleEnum.FMFO_AND_LocationFlush;
            case "6":
                return AllocRuleEnum.FMFO_FIFO_AND_LocationFlush;
            case "7":
                return AllocRuleEnum.FMFO_LIFO_AND_LocationFlush;
            case "8":
                return AllocRuleEnum.LIFO_AND_LocationFlush;
            case "9":
                return AllocRuleEnum.LocationFlush;
            case "10":
                return AllocRuleEnum.SalesOrderingUOMLocationFlush;
            case "11":
                return AllocRuleEnum.WeightBasedAllocation;
            default:
                return AllocRuleEnum.Select;
        }
    }
}
