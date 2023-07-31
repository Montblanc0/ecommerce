package it.ecommerce.controller;

import it.ecommerce.dto.OrdineDTO;
import it.ecommerce.model.Ordine;
import it.ecommerce.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ordini")
public class OrdineController {

	@Autowired
	private OrdineService ordineService;

	@PostMapping
	public String save(@RequestBody OrdineDTO dto) {
		return ordineService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ordineService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody OrdineDTO dto) {
		ordineService.update(id, dto);
	}

	@GetMapping("/{id}")
	public OrdineDTO getById(@PathVariable("id") Integer id) {
		return ordineService.getById(id);
	}


	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<OrdineDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return ordineService.query(pageable);
	}

	@GetMapping("/utente/{id}")
//	http://localhost:8080/ordini/utente/235?ord=stato,desc&q=5
	public Page<OrdineDTO> queryByUtente(@SortDefault(
			sort = "data",
			direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") Integer id) {
		return ordineService.queryByUtente(pageable, id);
	}

	//TEST
	@GetMapping("/test")
	public Ordine test() {
		return ordineService.test();
	}

	@GetMapping("/test2")
	public Ordine test2() {
		return ordineService.test2();
	}


}
