package com.edassist.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1969456482105496892L;

	public NotFoundException(String message) {
		super(message);
	}

}
