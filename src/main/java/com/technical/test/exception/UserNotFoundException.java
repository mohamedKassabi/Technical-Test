package com.technical.test.exception;

public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9217193742368011753L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
