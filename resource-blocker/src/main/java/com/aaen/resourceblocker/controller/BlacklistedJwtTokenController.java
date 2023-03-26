package com.aaen.resourceblocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.service.BlacklistedJwtTokenService;

@RestController
@RequestMapping(path = { "/api/v1/black-listed-token" })
public class BlacklistedJwtTokenController {

	@Autowired
	private BlacklistedJwtTokenService blacklistedJwtTokenService;

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin') and hasAuthority('blacklisted.delete.all')")
	public void deleteBlackListedToken() {
		blacklistedJwtTokenService.deleteBlacklistedJwtToken();
	}

}
