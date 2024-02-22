package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TradeTermEnumConverter implements AttributeConverter<TradeTermEnum,String>{

    @Override
    public String convertToDatabaseColumn(TradeTermEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public TradeTermEnum convertToEntityAttribute(String dbData) {
        return TradeTermEnum.fromShortName(dbData);
    }
    
}
