package com.anz.services.useraccounts.exception;

public final class ErrorResponse {
	
	private final int errorcode;
	private final String message;
	
	public ErrorResponse(int errorcode, String message) {
		this.errorcode = errorcode;
		this.message = message;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public String getMessage() {
		return message;
	}
	
}
