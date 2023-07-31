package it.ecommerce.controller;

import it.ecommerce.dto.OrdineStatoDTO;
import it.ecommerce.model.OrdineStato;
import it.ecommerce.service.OrdineStatoService;
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
@RequestMapping("/api/ordini/stati")
@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE')")
public class OrdineStatoController {

	@Autowired
	private OrdineStatoService ordineStatoService;

	@PostMapping
	public String save(@RequestBody OrdineStatoDTO dto) {
		return ordineStatoService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ordineStatoService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody OrdineStatoDTO dto) {
		ordineStatoService.update(id, dto);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping("/{id}")
	public OrdineStatoDTO getById(@PathVariable("id") Integer id) {
		return ordineStatoService.getById(id);
	}

	@PreAuthorize("hasAnyRole('AMMINISTRATORE', 'VENDITORE', 'CLIENTE')")
	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<OrdineStatoDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return ordineStatoService.query(pageable);
	}

	@GetMapping("/test")
	public OrdineStato test() {
		return ordineStatoService.test();
	}
}
