package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SavedSearchFilterTypeEnumConverter implements AttributeConverter<SavedSearchFilterTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(SavedSearchFilterTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public SavedSearchFilterTypeEnum convertToEntityAttribute(String dbData) {
        return SavedSearchFilterTypeEnum.fromShortName(dbData);
    }
    
}
