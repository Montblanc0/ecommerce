package it.ecommerce.controller;

import it.ecommerce.dto.CategoriaDTO;
import it.ecommerce.model.Categoria;
import it.ecommerce.service.CategoriaService;
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
@RequestMapping("/api/categorie")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public String save(@RequestBody CategoriaDTO dto) {
		return categoriaService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		categoriaService.delete(id);
	}

	@PutMapping("/{id}")
	//TODO: ritorna a (id, dto)
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody CategoriaDTO dto) {
		categoriaService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public CategoriaDTO getById(@PathVariable("id") Integer id) {
		return categoriaService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<CategoriaDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return categoriaService.query(pageable);
	}

	//TEST
	@GetMapping("test")
	public Categoria test() {
		return categoriaService.test();
	}
}
