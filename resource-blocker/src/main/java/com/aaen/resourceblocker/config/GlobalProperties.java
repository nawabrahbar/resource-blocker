package com.aaen.resourceblocker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class GlobalProperties {

	@Value("${jwt.clientSecret}")
	private String clientSecret;

	@Value("${jwt.accessTokenValiditity}")
	private Integer accessTokenValidity;

}
