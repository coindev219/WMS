package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ComplexityEnumConverter implements AttributeConverter<ComplexityEnum,String>{

    @Override
    public String convertToDatabaseColumn(ComplexityEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public ComplexityEnum convertToEntityAttribute(String dbData) {
        return ComplexityEnum.fromShortName(dbData);
    }
    
}
