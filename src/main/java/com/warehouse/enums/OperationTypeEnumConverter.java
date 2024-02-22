package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OperationTypeEnumConverter implements AttributeConverter<OperationTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(OperationTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public OperationTypeEnum convertToEntityAttribute(String dbData) {
        return OperationTypeEnum.fromShortName(dbData);
    }
    
}
