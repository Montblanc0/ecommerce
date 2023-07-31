package it.ecommerce.security.jwt;

import it.ecommerce.security.SecurityConstants;
import it.ecommerce.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Component
public class JWTFilter extends OncePerRequestFilter {


	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JWTUtil jwtUtil;


	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain chain)
			throws ServletException, IOException {

		// Get authorization header
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if ((null == header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		// Decode jwt token and get username
		final String token = header.split(" ")[1].trim();
		String username = jwtUtil.getSubject(token);
		if (null == username || "".equals(username)) {
			chain.doFilter(request, response);
			return;
		}

		// Get user identity and set it on the Spring Security context
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		Set<GrantedAuthority> authorities = null != userDetails ? (Set<GrantedAuthority>) userDetails.getAuthorities() : Collections.emptySet();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, authorities);

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
}
