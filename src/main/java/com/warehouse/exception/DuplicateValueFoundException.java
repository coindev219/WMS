package com.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateValueFoundException extends RuntimeException{
    private static final long serialVersionUID = 8554320715962995240L;
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public DuplicateValueFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
