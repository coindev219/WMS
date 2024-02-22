package com.warehouse.enums;

public enum SerialNoValidationRuleEnum {

    Select(""),
    NONE("1"),
    IS_SERIAL_NO_STARTING_WITH_SKU_CODE("2");
    

    private final String shortName;

    SerialNoValidationRuleEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static SerialNoValidationRuleEnum fromShortName(String shortName){

        switch (shortName){
            case "1":
                return SerialNoValidationRuleEnum.NONE;
            case "2":
                return SerialNoValidationRuleEnum.IS_SERIAL_NO_STARTING_WITH_SKU_CODE;
            default:
                return SerialNoValidationRuleEnum.Select;
        }
    }
}
