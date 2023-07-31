package it.ecommerce.controller;

import it.ecommerce.dto.OrdineNoteDTO;
import it.ecommerce.model.OrdineNote;
import it.ecommerce.service.OrdineNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ordini/note")
public class OrdineNoteController {

	@Autowired
	private OrdineNoteService ordineNoteService;

	@PostMapping
	public String save(@RequestBody OrdineNoteDTO dto) {
		return ordineNoteService.save(dto).toString();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ordineNoteService.delete(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable("id") Integer id,
	                   @RequestBody OrdineNoteDTO dto) {
		ordineNoteService.update(id, dto);
	}

	@GetMapping("/{id}")
	public OrdineNoteDTO getById(@PathVariable("id") Integer id) {
		return ordineNoteService.getById(id);
	}


	@GetMapping
//	http://localhost:8080/articoli?pagina=2&ord=pMarca.marca,asc&q=100
	public Page<OrdineNoteDTO> query(@SortDefault(
			sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {

		return ordineNoteService.query(pageable);
	}

	@GetMapping("/test")
	public OrdineNote test() {
		return ordineNoteService.test();
	}
}
