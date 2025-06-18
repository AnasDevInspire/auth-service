package com.inspire.core.auth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

@RestController
public class JwksController {

	private final JWKSet jwkSet;

	public JwksController(JWKSet jwkSet) {
		this.jwkSet = jwkSet;
	}

	@GetMapping("/.well-known/jwks.json")
	public Map<String, Object> keys() {
		return jwkSet.toJSONObject();
	}
}