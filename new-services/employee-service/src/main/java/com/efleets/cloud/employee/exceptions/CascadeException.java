package com.efleets.cloud.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CascadeException extends Exception {

	private static final long serialVersionUID = 2450463413013796871L;

	public CascadeException(Long id) {
		super("Cannot delete employee with id " + id + " due to them having direct reports");
	}
}
