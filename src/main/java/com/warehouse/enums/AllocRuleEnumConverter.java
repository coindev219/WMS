package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AllocRuleEnumConverter implements AttributeConverter<AllocRuleEnum,String>{

    @Override
    public String convertToDatabaseColumn(AllocRuleEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public AllocRuleEnum convertToEntityAttribute(String dbData) {
        return AllocRuleEnum.fromShortName(dbData);
    }
    
}
