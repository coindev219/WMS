package com.warehouse.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PrinterLabelTypeEnumConverter implements AttributeConverter<PrinterLabelTypeEnum,String>{

    @Override
    public String convertToDatabaseColumn(PrinterLabelTypeEnum attribute) {
        return attribute.getShortName();
    }

    @Override
    public PrinterLabelTypeEnum convertToEntityAttribute(String dbData) {
        return PrinterLabelTypeEnum.fromShortName(dbData);
    }
    
}
