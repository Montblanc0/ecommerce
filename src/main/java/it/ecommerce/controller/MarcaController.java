package it.ecommerce.controller;

import it.ecommerce.dto.MarcaDTO;
import it.ecommerce.model.Marca;
import it.ecommerce.service.MarcaService;
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
@RequestMapping("/api/marche")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class MarcaController {

	@Autowired
	private MarcaService marcaService;

	@PostMapping
	public String save(@RequestBody MarcaDTO dto) {
		return marcaService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		marcaService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody MarcaDTO dto) {
		marcaService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public MarcaDTO getById(@PathVariable("id") Integer id) {

		System.out.println("SERVICE");
		return marcaService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<MarcaDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return marcaService.query(pageable);
	}

	//TEST
	@GetMapping("test")
	public Marca test() {
		return marcaService.test();
	}
}
