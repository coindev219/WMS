package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UomVolumeEnumConverter implements AttributeConverter<UomVolumeEnum,String>{

    @Override
    public String convertToDatabaseColumn(UomVolumeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public UomVolumeEnum convertToEntityAttribute(String dbData) {
        return UomVolumeEnum.fromShortName(dbData);
    }
    
}
