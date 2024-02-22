package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SearchTypeEnumConverter implements AttributeConverter<SearchTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(SearchTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public SearchTypeEnum convertToEntityAttribute(String dbData) {
        return SearchTypeEnum.fromShortName(dbData);
    }
    
}
