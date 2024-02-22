package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class WeedDayEnumConverter implements AttributeConverter<WeekDayEnum,String>{

    @Override
    public String convertToDatabaseColumn(WeekDayEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public WeekDayEnum convertToEntityAttribute(String dbData) {
        return WeekDayEnum.fromShortName(dbData);
    }
    
}
