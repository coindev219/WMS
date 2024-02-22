package com.warehouse.enums;

public enum UserTypeEnum {

    Select(""),PowerUser("1"),WarehouseUser("2"), PrimaryCompanyUser("3"),CustomerUser("4"), 
    SupplierUser("5"), HHTUser("6"), NonTransactionalUser("7");
    

    private final String shortName;

    UserTypeEnum(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static UserTypeEnum fromShortName(String shortName) {

        switch (shortName) {
            case "1":
                return UserTypeEnum.PowerUser;
            case "2":
                return UserTypeEnum.WarehouseUser;
            case "3":
                return UserTypeEnum.PrimaryCompanyUser;
            case "4":
                return UserTypeEnum.CustomerUser;
            case "5":
                return UserTypeEnum.SupplierUser;
            case "6":
                return UserTypeEnum.HHTUser;
            case "7":
                return UserTypeEnum.NonTransactionalUser;
            default:
                return UserTypeEnum.Select;
        }
    }
}
