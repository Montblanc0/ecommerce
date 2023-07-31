package it.ecommerce.service;

import it.ecommerce.dto.UtenteDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Utente;
import it.ecommerce.repository.UtenteRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UtenteService extends AbstractService<Integer, UtenteDTO> {

	@Autowired
	private UtenteRepository utenteRepository;

	//TODO:
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private void validateUtente(Integer id) throws IllegalArgumentException {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(
				authority -> {
					if (!authority.getAuthority().contains("AMMINISTRATORE")) {
						String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
						UtenteDTO user = findByUsername(contextName).get();
						if (!Objects.equals(id, user.getId())) {
							throw new IllegalArgumentException("ID Utente non corrispondente");
						}
					}
				}
		);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer save(UtenteDTO dto) {
		// New users should use /api/auth/register
		dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		Utente bean = new Utente();
		BeanUtils.copyProperties(dto, bean);
		bean = utenteRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		validateUtente(id);
		utenteRepository.deleteById(id);
	}

	public void update(Integer id, UtenteDTO dto) {
		validateUtente(id);
		Utente bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		utenteRepository.save(bean);
	}

	public UtenteDTO getById(Integer id) {
		Utente original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public UtenteDTO getByUsername(String username) {
		UtenteDTO bean = new UtenteDTO();
		Utente original = requireOneByUsername(username);
		BeanUtils.copyProperties(original, bean);
		return bean;
	}

	public Optional<UtenteDTO> findByUsername(String username) {
		Optional<Utente> utenteOptional = utenteRepository.findByUsername(username);
		return utenteOptional.map(DTOMapper::toDTO);
	}

	public Page<UtenteDTO> query(Pageable pageable) {
		return utenteRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}

	public Page<UtenteDTO> queryByRole(Pageable pageable, Integer id) {
		return utenteRepository.findAllByRole(pageable, id).map(DTOMapper::toDTO);
	}

	private Utente requireOne(Integer id) {
		return utenteRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	private Utente requireOneByUsername(String username) {
		return utenteRepository.findByUsername(username)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + username));
	}

	// TEST
	@Transactional(rollbackFor = Exception.class)
	public Utente test() {
		Utente utente = utenteRepository.getById(1);
		Hibernate.initialize(utente);
		return utente;
	}
}
