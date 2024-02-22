package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PrinterOperationTypeEnumConverter implements AttributeConverter<PrinterOperationTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(PrinterOperationTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public PrinterOperationTypeEnum convertToEntityAttribute(String dbData) {
        return PrinterOperationTypeEnum.fromShortName(dbData);
    }
    
}
