package it.ecommerce.service;

import it.ecommerce.dto.AnagraficaDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Anagrafica;
import it.ecommerce.model.Utente;
import it.ecommerce.repository.AnagraficaRepository;
import it.ecommerce.repository.UtenteRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnagraficaService extends AbstractService<Integer, AnagraficaDTO> {

	@Autowired
	private AnagraficaRepository anagraficaRepository;
	@Autowired
	private UtenteRepository utenteRepository;

	private void validateUtente(Integer id) throws IllegalArgumentException {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(
				authority -> {
					if (!authority.getAuthority().contains("AMMINISTRATORE")) {
						String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
						Utente user = utenteRepository.findByAnagrafica(id).orElseThrow(() -> new IllegalArgumentException("ID Utente non corrispondente"));
						if (!Objects.equals(contextName, user.getUsername())) {
							throw new IllegalArgumentException("ID Utente non corrispondente");
						}
					}
				}
		);
	}

	public Integer save(AnagraficaDTO dto) {
		Anagrafica bean = new Anagrafica();
		BeanUtils.copyProperties(dto, bean);
		bean = anagraficaRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		validateUtente(id);
		anagraficaRepository.deleteById(id);
	}

	public void update(Integer id, AnagraficaDTO dto) {
		validateUtente(id);
		Anagrafica bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		anagraficaRepository.save(bean);
	}

	public AnagraficaDTO getById(Integer id) {
		Anagrafica original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<AnagraficaDTO> query(Pageable pageable) {
		return anagraficaRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private Anagrafica requireOne(Integer id) {
		return anagraficaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Anagrafica test() {
		Anagrafica anagrafica = anagraficaRepository.getById(2);
		Hibernate.initialize(anagrafica);
		return anagrafica;
	}
}
