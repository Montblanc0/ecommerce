package it.ecommerce.controller;

import it.ecommerce.dto.RuoloDTO;
import it.ecommerce.dto.UtenteDTO;
import it.ecommerce.model.Utente;
import it.ecommerce.repository.RuoloRepository;
import it.ecommerce.repository.UtenteRepository;
import it.ecommerce.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/utenti")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;
	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private RuoloRepository ruoloRepository;

	@PostMapping
	public String save(@RequestBody UtenteDTO dto) {
		return utenteService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		utenteService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody UtenteDTO dto) {
		utenteService.update(id, dto);
	}

	@GetMapping("/{id}")
	public UtenteDTO getById(@PathVariable("id") Integer id) {
		return utenteService.getById(id);
	}

	@GetMapping("/get/{username}")
	public UtenteDTO getByUsername(@PathVariable("username") String username) {
		return utenteService.getByUsername(username);
	}

	@GetMapping("/find/{username}")
	public ResponseEntity<Object> findByUsername(@PathVariable("username") String username) {
		Optional<UtenteDTO> utenteDtoOptional = utenteService.findByUsername(username);

		if (utenteDtoOptional.isPresent()) {
			return ResponseEntity.ok(utenteDtoOptional.get());
		} else {
			// 404 Not Found
			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
			response.put("message", "User not found: " + username);
			response.put("path", "/utenti/find/" + username);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}


	}

	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<UtenteDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return utenteService.query(pageable);
	}

	@GetMapping("/ruolo/{id}")
//	http://localhost:8080/utenti/ruolo/2?pagina=2&q=30
	public Page<UtenteDTO> queryByRole(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") Integer id) {

		return utenteService.queryByRole(pageable, id);
	}

	//TEST

	@GetMapping("/test2")
	public Set<RuoloDTO> getField() {
		return ruoloRepository.findRuoloByIdUtente(20).get();
	}


	@GetMapping("/test")
	public Utente test2() {
		return utenteService.test();
	}

	@GetMapping("/info")
	public Utente getUserDetails() {
		// Retrieve username from the Security Context
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Fetch and return entity
		return utenteRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nessun utente trovate con quell'username"));
	}


}
