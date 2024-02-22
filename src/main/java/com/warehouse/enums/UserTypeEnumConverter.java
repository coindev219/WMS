package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeEnumConverter implements AttributeConverter<UserTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(UserTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public UserTypeEnum convertToEntityAttribute(String dbData) {
        return UserTypeEnum.fromShortName(dbData);
    }
    
}
