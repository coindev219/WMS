package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ShipModeEnumConverter implements AttributeConverter<ShipModeEnum,String>{

    @Override
    public String convertToDatabaseColumn(ShipModeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public ShipModeEnum convertToEntityAttribute(String dbData) {
        return ShipModeEnum.fromShortName(dbData);
    }
    
}
