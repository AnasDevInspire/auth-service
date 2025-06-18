package com.inspire.core.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inspire.core.auth.AuthRequest;
import com.inspire.core.auth.service.AuthService;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest req) {
		String token = authService.getToken(req);
		return ResponseEntity.ok(Map.of("token", token));
	}

}