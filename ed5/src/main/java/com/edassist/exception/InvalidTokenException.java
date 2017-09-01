package com.edassist.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
	public InvalidTokenException(String message) {
		super(message);
	}
}
