package it.ecommerce.controller;

import it.ecommerce.dto.SottocategoriaDTO;
import it.ecommerce.model.Sottocategoria;
import it.ecommerce.service.SottocategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sottocategorie")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class SottocategoriaController {

	@Autowired
	private SottocategoriaService sottocategoriaService;

	@PostMapping
	public String save(@RequestBody SottocategoriaDTO dto) {
		return sottocategoriaService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		sottocategoriaService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody SottocategoriaDTO dto) {
		sottocategoriaService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public SottocategoriaDTO getById(@PathVariable("id") Integer id) {
		return sottocategoriaService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<SottocategoriaDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return sottocategoriaService.query(pageable);
	}

	//TEST


	@GetMapping("/test")
	public Sottocategoria test() {
		return sottocategoriaService.test();
	}
}
