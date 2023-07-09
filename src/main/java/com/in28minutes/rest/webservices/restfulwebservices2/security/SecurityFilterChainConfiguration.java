package com.in28minutes.rest.webservices.restfulwebservices2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityFilterChainConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// All requests should be authenticated. 
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		// If a request is not authenticated then a web page is shown
		http.httpBasic(withDefaults());
		// CSRF -> Post and Put 
		http.csrf().disable();
		return http.build();
	}
	
}
