package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VehicleTypeEnumConverter implements AttributeConverter<VehicleTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(VehicleTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public VehicleTypeEnum convertToEntityAttribute(String dbData) {
        return VehicleTypeEnum.fromShortName(dbData);
    }
    
}
