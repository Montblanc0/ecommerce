package it.ecommerce.controller;

import it.ecommerce.dto.IndirizzoDTO;
import it.ecommerce.model.Indirizzo;
import it.ecommerce.service.IndirizzoService;
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
@RequestMapping("/api/utenti/indirizzi")
public class IndirizzoController {

	@Autowired
	private IndirizzoService indirizzoService;

	@PostMapping
	public String save(@Valid @RequestBody IndirizzoDTO dto) {
		return indirizzoService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
		indirizzoService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@Valid @NotNull @PathVariable("id") Integer id,
	                   @Valid @RequestBody IndirizzoDTO dto) {
		indirizzoService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public IndirizzoDTO getById(@Valid @NotNull @PathVariable("id") Integer id) {
		return indirizzoService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
	public Page<IndirizzoDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return indirizzoService.query(pageable);
	}

	//TEST
	@GetMapping("test")
	public Indirizzo test() {
		return indirizzoService.test();
	}
}
