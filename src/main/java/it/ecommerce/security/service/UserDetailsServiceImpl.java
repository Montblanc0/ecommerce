package it.ecommerce.security.service;

import it.ecommerce.dto.RuoloDTO;
import it.ecommerce.model.UtenteAuth;
import it.ecommerce.repository.RuoloRepository;
import it.ecommerce.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private RuoloRepository ruoloRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, EntityNotFoundException {

		UtenteAuth utente = utenteRepository.findByUsernameForAuthentication(username).orElseThrow(() -> new UsernameNotFoundException("Resource not found: " + username));

		Set<RuoloDTO> ruoliDTO = ruoloRepository.findRuoloByIdUtente(utente.getId()).orElseThrow(() -> new EntityNotFoundException("No roles associated with user"));

		Set<GrantedAuthority> authorities = new HashSet<>();

		ruoliDTO.forEach(ruolo -> authorities.add(new SimpleGrantedAuthority(ruolo.getRuolo())));

		return new org.springframework.security.core.userdetails.User(
				username,
				utente.getPassword(),
				authorities);
	}
}
