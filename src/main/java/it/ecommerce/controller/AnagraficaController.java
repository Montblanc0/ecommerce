package it.ecommerce.controller;

import it.ecommerce.dto.AnagraficaDTO;
import it.ecommerce.model.Anagrafica;
import it.ecommerce.service.AnagraficaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/utenti/anagrafiche")
public class AnagraficaController {

	@Autowired
	private AnagraficaService anagraficaService;

	@PostMapping
	public String save(@RequestBody AnagraficaDTO dto) {
		return anagraficaService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		anagraficaService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody AnagraficaDTO dto) {
		anagraficaService.update(id, dto);
	}

	@GetMapping("/{id}")
	public AnagraficaDTO getById(@PathVariable("id") Integer id) {
		return anagraficaService.getById(id);
	}

	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<AnagraficaDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return anagraficaService.query(pageable);
	}

	//TEST
	@GetMapping("test")
	public Anagrafica test() {
		return anagraficaService.test();
	}
}
