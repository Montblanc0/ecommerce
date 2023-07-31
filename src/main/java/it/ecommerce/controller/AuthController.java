package it.ecommerce.controller;

import it.ecommerce.dto.LoginDTO;
import it.ecommerce.model.Anagrafica;
import it.ecommerce.model.Utente;
import it.ecommerce.repository.AnagraficaRepository;
import it.ecommerce.repository.RuoloRepository;
import it.ecommerce.repository.UtenteRepository;
import it.ecommerce.security.jwt.JWTUtil;
import it.ecommerce.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AnagraficaRepository anagraficaRepository;
	@Autowired
	RuoloRepository ruoloRepository;
	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserDetailsServiceImpl uds;


	@Transactional
	@PostMapping("/register")
	public UserDetails registerHandler(@RequestBody Utente user) {
		String encodedPass = encoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		Anagrafica savedAnagrafica = anagraficaRepository.save(user.getAnagrafica());
		user.setAnagrafica(savedAnagrafica);
		user = utenteRepository.save(user);
		return uds.loadUserByUsername(user.getUsername());
	}

	@PostMapping("/login")

	public Map<String, Object> loginHandler(@RequestBody LoginDTO body) {
		try {
			// Creating the Authentication Token
			UsernamePasswordAuthenticationToken authInputToken =
					new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

			// Authenticating the Login Credentials
			Authentication auth = authManager.authenticate(authInputToken);

			// Update the context by setting the Authentication Object
			SecurityContextHolder.getContext().setAuthentication(auth);

			// Generate the JWT
			String token = jwtUtil.encode(auth);

			// Respond with the JWT
			return Collections.singletonMap("jwt-token", token);
		} catch (AuthenticationException authExc) {
			SecurityContextHolder.getContext().setAuthentication(null);
			throw new RuntimeException("Invalid Login Credentials");
		}
	}

	//TEST
	@GetMapping("/test")
	public Utente getUserDetails() {
		// Retrieve username from the Security Context
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// Fetch and return user details
		return utenteRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nessun utente trovato con quell'username"));
	}
}

