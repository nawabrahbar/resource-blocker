package com.aaen.resourceblocker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException(String exception) {
		super(exception);
	}
}
