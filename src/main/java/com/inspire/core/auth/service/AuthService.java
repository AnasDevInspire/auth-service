package com.inspire.core.auth.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.inspire.core.auth.AuthRequest;

@Service
public class AuthService {

	private final AuthenticationManager authManager;
	private final JwtEncoder jwtEncoder;

	public AuthService(AuthenticationManager authManager, JwtEncoder jwtEncoder) {
		this.authManager = authManager;
		this.jwtEncoder = jwtEncoder;
	}

	public String getToken(AuthRequest req) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

		Instant now = Instant.now();
		String token = jwtEncoder
				.encode(JwtEncoderParameters.from(JwtClaimsSet.builder().subject(auth.getName()).issuedAt(now)
						.expiresAt(now.plusSeconds(3600)).claim("roles", auth.getAuthorities()).build()))
				.getTokenValue();
		return token;
	}
}
