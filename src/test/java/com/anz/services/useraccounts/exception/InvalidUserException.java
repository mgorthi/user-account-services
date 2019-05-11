package com.anz.services.useraccounts.exception;

public class InvalidUserException extends Exception {
	
	private static final long serialVersionUID = 272302680521145341L;

	public InvalidUserException() {
		super();
	}
	
	public InvalidUserException(String message) {
		super(message);
	}
	
	public InvalidUserException(Throwable t) {
		super(t);
	}
	
	public InvalidUserException(String message, Throwable t) {
		super(message, t);
	}

}
