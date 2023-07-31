package it.ecommerce.service;

import it.ecommerce.dto.OrdineDTO;
import it.ecommerce.dto.OrdineDettagliDTO;
import it.ecommerce.dto.OrdineNoteDTO;
import it.ecommerce.dto.OrdineStatoDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.*;
import it.ecommerce.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdineService extends AbstractService<Integer, OrdineDTO> {

	@Autowired
	private OrdineRepository ordineRepository;
	@Autowired
	private SpedizioneRepository spedizioneRepository;
	@Autowired
	private OrdineStatoRepository ordineStatoRepository;
	@Autowired
	private IndirizzoRepository indirizzoRepository;
	@Autowired
	private ArticoloRepository articoloRepository;
	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private OrdineDettagliRepository ordineDettagliRepository;
	@Autowired
	private OrdineNoteRepository ordineNoteRepository;

	private void validateCliente(OrdineDTO dto) throws IllegalArgumentException {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(
				authority -> {
					if (authority.getAuthority().contains("CLIENTE")) {
						String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
						if (!Objects.equals(contextName, dto.getCliente().getUsername())) {
							throw new IllegalArgumentException("Logged user's username doesnt match order's");
						}
					}
				}
		);
	}

	public Integer save(OrdineDTO dto) {
		// Validate user id and throw an error if a "CLIENTE" tries to create/update
		// an order belonging to another user
		validateCliente(dto);
		// Throw an error if quantity > availability
		dto.getDettagli().forEach(d -> {
			Integer quantita = d.getQuantita();
			Integer idArticolo = d.getArticolo().getId();
			Articolo articolo = articoloRepository.getById(idArticolo);
			if (quantita > articolo.getGiacenza())
				throw new IllegalArgumentException("Quantità richiesta non disponibile per il prodotto: " + articolo.getNome() + " " + articolo.getModello());
		});
		// Save Ordine
		Ordine bean = toEntity(dto);
		bean = ordineRepository.save(bean);
		// Save OrdineNote
		OrdineNote note = ordineNoteToEntity(dto.getNote(), bean);
		OrdineNote savedNote = ordineNoteRepository.save(note);
		// Save OrdineDettagli
		Set<OrdineDettagli> dettagli = ordineDettagliToEntity(dto.getDettagli(), bean);
		List<OrdineDettagli> savedDettagli = ordineDettagliRepository.saveAll(dettagli);
		// Update Ordine with OrdineNote and OrdineDettagli
		bean.setNote(savedNote);
		bean.setDettagli(new HashSet<>(savedDettagli));
		bean = ordineRepository.save(bean);
		return bean.getId();
	}

	private Ordine toEntity(OrdineDTO dto) {

		//TODO: orElseThrow
		Ordine ordine = new Ordine();
		BeanUtils.copyProperties(dto, ordine);

		// OrdineStato
		if (null != dto.getId()) {
			// Get reference to existing order's status
			Integer id = dto.getId();
			if (ordineRepository.findById(id).isPresent()) {
				OrdineStatoDTO statoDTO = dto.getStato();
				OrdineStato stato = ordineStatoRepository.getById(statoDTO.getId());
				ordine.setStato(stato);
			} else throw new IllegalArgumentException("Ordine con id: " + id + " non trovato.");
		} else {
			// Set initial state
			OrdineStato stato = ordineStatoRepository.getById(0);
			ordine.setStato(stato);
		}
		System.out.println("ORDINE CON STATO: " + dto.getCliente());
		// Cliente
		Utente cliente = utenteRepository.getById(dto.getCliente().getId());
		ordine.setCliente(cliente);
		System.out.println("CLIENTE: " + cliente);
		// Indirizzo
		Indirizzo ind = indirizzoRepository.getById(dto.getIndirizzo().getId());
		ordine.setIndirizzo(ind);
		// Spedizione
		Spedizione sped = spedizioneRepository.getById(dto.getSpedizione().getId());
		ordine.setSpedizione(sped);

		return ordine;
	}

	private Set<OrdineDettagli> ordineDettagliToEntity(Set<OrdineDettagliDTO> dtos, Ordine ordine) {
		// OrdineDettagli
		Set<OrdineDettagli> dettagli = new HashSet<>();
		dtos.forEach(d -> {
			OrdineDettagli dettaglio = new OrdineDettagli();
			BeanUtils.copyProperties(d, dettaglio);

			// Articolo
			Articolo articolo = articoloRepository.getById(d.getArticolo().getId());
			dettaglio.setArticolo(articolo);
			dettaglio.setOrdine(ordine);
			dettagli.add(dettaglio);
		});
		return dettagli;
	}

	private OrdineNote ordineNoteToEntity(OrdineNoteDTO dto, Ordine ordine) {
		// OrdineNote
		OrdineNote note = new OrdineNote();
		BeanUtils.copyProperties(dto, note);
		note.setOrdine(ordine);
		return note;
	}

	public void delete(Integer id) {
		ordineRepository.deleteById(id);
	}

	public void update(Integer id, OrdineDTO dto) {
		validateCliente(dto);
		Ordine bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		ordineRepository.save(bean);
	}

	public OrdineDTO getById(Integer id) {
		Ordine original = requireOne(id);
		return DTOMapper.toDTO(original);
	}


	public Page<OrdineDTO> query(Pageable pageable) {
		return ordineRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}

	public Page<OrdineDTO> queryByUtente(Pageable pageable, Integer id) {
		return ordineRepository.findAllByUtente(pageable, id).map(DTOMapper::toDTO);
	}

	private Ordine requireOne(Integer id) {
		return ordineRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	// TEST

	public Ordine test2() {
		Ordine ordine = ordineRepository.getById(902);
		Hibernate.initialize(ordine);
		Hibernate.initialize(ordine.getCliente());
		Hibernate.initialize(ordine.getIndirizzo());
		Hibernate.initialize(ordine.getSpedizione());
		Hibernate.initialize(ordine.getDettagli());
		ordine.getDettagli().forEach(det -> Hibernate.initialize(det.getArticolo()));
		Hibernate.initialize(ordine.getStato());
		return ordine;
	}

	@Transient
	public Ordine test() {
		Ordine ordine = new Ordine();
		Utente utente = new Utente();
		utente.setId(4);
		ordine.setCliente(utente);
		OrdineNote nota = new OrdineNote();
		nota.setNota("Nuovo ordine");
		ordine.setNote(nota);
		Spedizione sped = new Spedizione();
		sped.setId(3);
		ordine.setSpedizione(sped);
		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setId(3);
		ordine.setIndirizzo(indirizzo);
		Set<OrdineDettagli> dettagli = new HashSet<>();
		OrdineDettagli dett = new OrdineDettagli();
		Articolo art = new Articolo();
		art.setId(2083);
		dett.setArticolo(art);
		dett.setQuantita(3);
		dettagli.add(dett);
		ordine.setDettagli(dettagli);
		return ordineRepository.save(ordine);
	}
}
