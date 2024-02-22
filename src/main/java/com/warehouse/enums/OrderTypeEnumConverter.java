package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderTypeEnumConverter implements AttributeConverter<OrderTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(OrderTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public OrderTypeEnum convertToEntityAttribute(String dbData) {
        return OrderTypeEnum.fromShortName(dbData);
    }
    
}
