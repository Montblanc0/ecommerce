package it.ecommerce.service;

import it.ecommerce.dto.OrdineNoteDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.OrdineNote;
import it.ecommerce.model.Utente;
import it.ecommerce.repository.OrdineNoteRepository;
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
@Transactional
public class OrdineNoteService extends AbstractService<Integer, OrdineNoteDTO> {

	@Autowired
	private OrdineNoteRepository ordineNoteRepository;
	@Autowired
	private UtenteRepository utenteRepository;


	private void validateUtente(Integer id) throws IllegalArgumentException {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(
				authority -> {
					if (!authority.getAuthority().contains("AMMINISTRATORE")) {
						String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
						Utente user = utenteRepository.findByOrdineNote(id).orElseThrow(() -> new IllegalArgumentException("ID Utente non corrispondente"));
						if (!Objects.equals(contextName, user.getUsername())) {
							throw new IllegalArgumentException("ID Utente non corrispondente");
						}
					}
				}
		);
	}

	public Integer save(OrdineNoteDTO dto) {
		OrdineNote bean = new OrdineNote();
		BeanUtils.copyProperties(dto, bean);
		bean = ordineNoteRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		validateUtente(id);
		ordineNoteRepository.deleteById(id);
	}

	public void update(Integer id, OrdineNoteDTO dto) {
		validateUtente(id);
		OrdineNote bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		ordineNoteRepository.save(bean);
	}

	public OrdineNoteDTO getById(Integer id) {
		OrdineNote original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<OrdineNoteDTO> query(Pageable pageable) {
		return ordineNoteRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private OrdineNote requireOne(Integer id) {
		return ordineNoteRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	// TEST
	public OrdineNote test() {
		OrdineNote note = ordineNoteRepository.getById(802);
		Hibernate.initialize(note);
		return note;
	}
}
