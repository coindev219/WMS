package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UomLengthEnumConverter implements AttributeConverter<UomLengthEnum,String>{

    @Override
    public String convertToDatabaseColumn(UomLengthEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public UomLengthEnum convertToEntityAttribute(String dbData) {
        return UomLengthEnum.fromShortName(dbData);
    }
    
}
