package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UomCategoryEnumConverter implements AttributeConverter<UomCategoryEnum,String>{

    @Override
    public String convertToDatabaseColumn(UomCategoryEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public UomCategoryEnum convertToEntityAttribute(String dbData) {
        return UomCategoryEnum.fromShortName(dbData);
    }
    
}
