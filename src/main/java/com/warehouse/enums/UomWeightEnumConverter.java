package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UomWeightEnumConverter implements AttributeConverter<UomWeightEnum,String>{

    @Override
    public String convertToDatabaseColumn(UomWeightEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public UomWeightEnum convertToEntityAttribute(String dbData) {
        return UomWeightEnum.fromShortName(dbData);
    }
    
}
