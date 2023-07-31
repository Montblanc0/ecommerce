package it.ecommerce.controller;

import it.ecommerce.dto.RuoloDTO;
import it.ecommerce.model.Ruolo;
import it.ecommerce.service.RuoloService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/ruoli")
public class RuoloController {

	@Autowired
	private RuoloService ruoloService;

	@PostMapping
	public String save(@RequestBody RuoloDTO dto) {
		return ruoloService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ruoloService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody RuoloDTO dto) {
		ruoloService.update(id, dto);
	}


	@GetMapping("/{id}")
	public RuoloDTO getById(@PathVariable("id") Integer id) {
		return ruoloService.getById(id);
	}

	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<RuoloDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return ruoloService.query(pageable);
	}


	@GetMapping("/test")
	public Ruolo test() {
		return ruoloService.test();
	}
}
