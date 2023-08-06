package it.ecommerce.security;

import it.ecommerce.security.jwt.JWTFilter;
import it.ecommerce.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.AUD;
import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.SUB;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@ComponentScan({"it.ecommerce.service", "it.ecommerce.config", "it.ecommerce.security.jwt"})
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)

public class SecurityConfig {

	private final BCryptPasswordEncoder encoder;

	private final UserDetailsServiceImpl uds;

	@Autowired
	private JWTFilter filter;

	@Value("classpath:certs/public.pem")
	private RSAPublicKey key;


	@Bean
	public JwtDecoder jwtDecoder() {
		final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(this.key).build();
		decoder.setJwtValidator(tokenValidator());
		return decoder;
	}

	public OAuth2TokenValidator<Jwt> tokenValidator() {
		final List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
		validators.add(new JwtTimestampValidator());
		validators.add(new JwtIssuerValidator("ecommerce"));
		validators.add(audienceValidator());
		validators.add(subValidator());
		return new DelegatingOAuth2TokenValidator<>(validators);
	}

	public OAuth2TokenValidator<Jwt> subValidator() {
		return new JwtClaimValidator<String>(SUB, s -> null != s
				&& null != SecurityContextHolder.getContext().getAuthentication()
				&& s.equals(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	public OAuth2TokenValidator<Jwt> audienceValidator() {
		return new JwtClaimValidator<List<String>>(AUD, aud -> null != aud && aud.contains("ecommerce"));
	}


	//TODO: Authorizations
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf().disable()
				.cors()
				.and()
				.authorizeHttpRequests(authorize ->
						authorize
								.antMatchers("/api/auth/register").permitAll()
								.antMatchers("/api/auth/login").permitAll()
								.antMatchers("/api/auth/ruoli").hasRole("AMMINISTRATORE")
								.anyRequest().authenticated())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(uds)
				.passwordEncoder(encoder);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
//	@Profile("roles")
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}