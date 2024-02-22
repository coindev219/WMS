package com.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidValueFoundException extends RuntimeException{
    private static final long serialVersionUID = 8554320715962995240L;
	private String resourceName;
	

	public InvalidValueFoundException(String resourceName,String message) {
		super(message);
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	
}
