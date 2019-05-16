package com.anz.services.useraccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAccountsExceptionAdvice {
	
	@ResponseBody
	@ExceptionHandler(InvalidUserException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse invalidUserExceptionHandler(InvalidUserException ex) {
		return new ErrorResponse(1, ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse invalidUserExceptionHandler(Exception ex) {
		return new ErrorResponse(2, ex.getMessage());
	}

}
