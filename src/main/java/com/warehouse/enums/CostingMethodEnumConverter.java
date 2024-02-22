package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CostingMethodEnumConverter implements AttributeConverter<CostingMethodEnum,String>{

    @Override
    public String convertToDatabaseColumn(CostingMethodEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public CostingMethodEnum convertToEntityAttribute(String dbData) {
        return CostingMethodEnum.fromShortName(dbData);
    }
    
}
