package com.aaen.resourceblocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.exception.RecordNotFoundException;
import com.aaen.resourceblocker.model.Authentication;
import com.aaen.resourceblocker.service.AuthenticationService;

@RestController
@RequestMapping(path = { "/api/v1" })
public class AuthenticationController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class.getName());

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> validateUser(@RequestBody Authentication authentication) {

		try {
			if (authentication.getEmail() != null)
				authentication.setEmail(authentication.getEmail().toLowerCase());
			return authenticationService.validateUser(authentication);
		} catch (BadCredentialsException badCredentialsException) {
			return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
		} catch (RecordNotFoundException recordNotFoundException) {
			return new ResponseEntity<>("Invalid email id", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/invalidate-token", method = RequestMethod.GET)
	public ResponseEntity<?> invalidateToken(@RequestHeader(name = "Authorization") String token) {
		String response = authenticationService.invalidateToken(token);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			throw new RecordNotFoundException("Not added.");
		}
	}

}
