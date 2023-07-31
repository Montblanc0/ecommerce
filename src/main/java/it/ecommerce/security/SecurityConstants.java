package it.ecommerce.security;

import java.time.Duration;

public class SecurityConstants {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String SIGN_UP_URL = "/api/auth/register";
	public static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(60);
}