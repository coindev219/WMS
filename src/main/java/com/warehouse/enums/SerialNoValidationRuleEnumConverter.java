package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SerialNoValidationRuleEnumConverter implements AttributeConverter<SerialNoValidationRuleEnum,String>{

    @Override
    public String convertToDatabaseColumn(SerialNoValidationRuleEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public SerialNoValidationRuleEnum convertToEntityAttribute(String dbData) {
        return SerialNoValidationRuleEnum.fromShortName(dbData);
    }
    
}
