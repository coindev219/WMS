package com.warehouse.enums;

public enum TransactionTypeEnum {

    Select(""), AllocatedForSorting("1"), CartonDamage("2"), DamagedUnit("3"), Excess("4"), Graded("5"), Insured("6"),
    KitToStore("7"), Returned("8"), SalesOrderAllocation("9");

    private final String shortName;

    TransactionTypeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static TransactionTypeEnum fromShortName(String shortName) {
        switch (shortName) {
            case "1":
                return TransactionTypeEnum.AllocatedForSorting;
            case "2":
                return TransactionTypeEnum.CartonDamage;
            case "3":
                return TransactionTypeEnum.DamagedUnit;
            case "4":
                return TransactionTypeEnum.Excess;
            case "5":
                return TransactionTypeEnum.Graded;
            case "6":
                return TransactionTypeEnum.Insured;
            case "7":
                return TransactionTypeEnum.KitToStore;
            case "8":
                return TransactionTypeEnum.Returned;
            case "9":
                return TransactionTypeEnum.SalesOrderAllocation;
            default:
                return TransactionTypeEnum.Select;
        }
    }

}
