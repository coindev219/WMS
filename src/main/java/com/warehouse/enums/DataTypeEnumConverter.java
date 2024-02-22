package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DataTypeEnumConverter implements AttributeConverter<DataTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(DataTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public DataTypeEnum convertToEntityAttribute(String dbData) {
        return DataTypeEnum.fromShortName(dbData);
    }
    
}
