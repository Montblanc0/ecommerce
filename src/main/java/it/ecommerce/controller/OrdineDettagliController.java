package it.ecommerce.controller;

import it.ecommerce.dto.OrdineDettagliDTO;
import it.ecommerce.model.OrdineDettagli;
import it.ecommerce.service.OrdineDettagliService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ordini/dettagli")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class OrdineDettagliController {

	@Autowired
	private OrdineDettagliService ordineDettagliService;

	@PostMapping
	public String save(@RequestBody OrdineDettagliDTO dto) {
		return ordineDettagliService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ordineDettagliService.delete(id);
	}

	@PutMapping("/{id}")
	public List<OrdineDettagliDTO> update(@RequestBody List<OrdineDettagliDTO> dtos) {
		return ordineDettagliService.update(dtos);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public List<OrdineDettagliDTO> getById(@PathVariable("id") Integer id) {
		return ordineDettagliService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<OrdineDettagliDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return ordineDettagliService.query(pageable);
	}

	@GetMapping("/test")
	public List<OrdineDettagli> test() {
		return ordineDettagliService.test();
	}
}
