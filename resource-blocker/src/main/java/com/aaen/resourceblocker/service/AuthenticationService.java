package com.aaen.resourceblocker.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.exception.RecordNotFoundException;
import com.aaen.resourceblocker.model.Authentication;

@Component
@Qualifier("AuthenticationService")
public interface AuthenticationService {

	public ResponseEntity<?> validateUser(Authentication authentication)
			throws BadCredentialsException, RecordNotFoundException;

	public String invalidateToken(String token);

}
