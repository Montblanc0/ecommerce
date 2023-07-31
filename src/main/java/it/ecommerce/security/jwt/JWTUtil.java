package it.ecommerce.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collection;

import static it.ecommerce.security.SecurityConstants.JWT_TOKEN_VALIDITY;

@Component
public class JWTUtil {


	private final Algorithm alg;


	public JWTUtil(@Value("classpath:certs/public.pem") final RSAPublicKey publicKey,
	               @Value("classpath:certs/private.pem") final RSAPrivateKey privateKey) {
		this.alg = Algorithm.RSA256(publicKey, privateKey);
	}


	public String encode(Authentication auth) throws IllegalArgumentException, JWTCreationException {
		String username = auth.getName();
		final Instant now = Instant.now();
		final Instant end = now.plusMillis(JWT_TOKEN_VALIDITY.toMillis());
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String[] roles = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new);
		return JWT.create()
				.withSubject(username)
				.withIssuer("ecommerce")
				.withIssuedAt(now)
				.withExpiresAt(end)
				.withArrayClaim("roles", roles)
				.withAudience("ecommerce")
				.sign(this.alg);
	}


	public String decode(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(this.alg).build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.toString();
	}

	// Extracts username from the token payload
	public String getSubject(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(this.alg).build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getSubject();
	}

}