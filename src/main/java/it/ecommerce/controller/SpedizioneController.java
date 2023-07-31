package it.ecommerce.controller;

import it.ecommerce.dto.SpedizioneDTO;
import it.ecommerce.model.Spedizione;
import it.ecommerce.service.SpedizioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spedizioni")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class SpedizioneController {

	@Autowired
	private SpedizioneService spedizioneService;

	@PostMapping
	public String save(@Valid @RequestBody SpedizioneDTO dto) {
		return spedizioneService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
		spedizioneService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@Valid @NotNull @PathVariable("id") Integer id,
	                   @Valid @RequestBody SpedizioneDTO dto) {
		spedizioneService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public SpedizioneDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
		return spedizioneService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<SpedizioneDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return spedizioneService.query(pageable);
	}

	//TEST

	@GetMapping("test")
	public Spedizione test() {
		return spedizioneService.test();
	}
}
