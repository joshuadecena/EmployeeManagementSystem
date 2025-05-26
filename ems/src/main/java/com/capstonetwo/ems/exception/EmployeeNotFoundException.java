package com.capstonetwo.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Long id, String name) {
        super(String.format("%s not found with %s : '%s'", id, name));
    }
} 