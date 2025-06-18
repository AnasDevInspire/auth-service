package com.inspire.core.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();

		UserDetails emp = User.withUsername("emp").password("{noop}emp123").roles("EMPLOYEE").build();

		return new InMemoryUserDetailsManager(admin, emp);
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService uds) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(uds);
		return new ProviderManager(provider);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  http
	      .csrf(csrf -> csrf.disable())
	      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	      .authorizeHttpRequests(auth -> auth
	          .requestMatchers(HttpMethod.POST, "/login").permitAll()
	          .requestMatchers(HttpMethod.GET,  "/.well-known/jwks.json").permitAll()
	          .anyRequest().authenticated()
	      );

	    return http.build();
	}
}