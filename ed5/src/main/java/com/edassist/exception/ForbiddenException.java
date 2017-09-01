package com.edassist.exception;

import com.edassist.constants.Constants;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -5135453953778839666L;

	public ForbiddenException() {
		super(Constants.REST_FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(message);
	}

}
