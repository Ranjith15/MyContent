package com.edassist.exception;

import com.edassist.constants.Constants;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -5760575910055010783L;

	public UnauthorizedException() {
		super(Constants.REST_UNAUTHORIZED);
	}

	public UnauthorizedException(String message) {
		super(message);
	}
}
