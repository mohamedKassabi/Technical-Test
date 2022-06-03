package com.technical.test.exception;

public class InvalidUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018679731990525380L;

	public InvalidUserException(String message) {
		super(message);
	}

}
