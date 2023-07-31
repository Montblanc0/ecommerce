package it.ecommerce.controller;

import it.ecommerce.dto.ArticoloDTO;
import it.ecommerce.model.Articolo;
import it.ecommerce.service.ArticoloService;
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
@RequestMapping("/api/articoli")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class ArticoloController {

	@Autowired
	private ArticoloService articoloService;

	@PostMapping
	public String save(@RequestBody ArticoloDTO dto) {
		return articoloService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		articoloService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody ArticoloDTO dto) {
		articoloService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public ArticoloDTO getById(@PathVariable("id") Integer id) {
		return articoloService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<ArticoloDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return articoloService.query(pageable);
	}

	//TEST
	@GetMapping("test")
	public Articolo test() {
		return articoloService.test();
	}

}
