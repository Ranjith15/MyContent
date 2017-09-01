package com.edassist.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidSignatureException extends AuthenticationException {
	public InvalidSignatureException(String message) {
		super(message);
	}
}
