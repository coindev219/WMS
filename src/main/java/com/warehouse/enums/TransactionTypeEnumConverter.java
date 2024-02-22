package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeEnumConverter implements AttributeConverter<TransactionTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(TransactionTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public TransactionTypeEnum convertToEntityAttribute(String dbData) {
        return TransactionTypeEnum.fromShortName(dbData);
    }
    
}
