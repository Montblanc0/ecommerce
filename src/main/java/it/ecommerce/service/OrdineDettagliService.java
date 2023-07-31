package it.ecommerce.service;

import it.ecommerce.dto.OrdineDettagliDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.OrdineDettagli;
import it.ecommerce.repository.OrdineDettagliRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrdineDettagliService {

	@Autowired
	private OrdineDettagliRepository ordineDettagliRepository;

	public Map<String, Integer> save(OrdineDettagliDTO dto) {
		OrdineDettagli bean = new OrdineDettagli();
		BeanUtils.copyProperties(dto, bean);
		bean = ordineDettagliRepository.save(bean);
		Map<String, Integer> map = new HashMap<>();
		map.put("p_Ordine", bean.getOrdine().getId());
		map.put("p_Articolo", bean.getArticolo().getId());
		return map;
	}

	public void delete(Integer id) {
		ordineDettagliRepository.deleteByOrdine(id);
	}

	public List<OrdineDettagliDTO> update(List<OrdineDettagliDTO> dtos) {
		List<OrdineDettagli> updated = new ArrayList<>();
		for (OrdineDettagliDTO dto : dtos) {
			OrdineDettagli entity = getByOrdineAndArticolo(dto.getIdOrdine(), dto.getIdArticolo());
			BeanUtils.copyProperties(dto, entity);
			updated.add(entity);
		}
		ordineDettagliRepository.saveAllAndFlush(updated);
		List<OrdineDettagliDTO> dettagliDTO = new LinkedList<>();
		updated.forEach(dettaglio -> dettagliDTO.add(DTOMapper.toDTO(dettaglio)));
		return dettagliDTO;
	}

	public List<OrdineDettagliDTO> getById(Integer id) {
		List<OrdineDettagli> originals = requireList(id);
		List<OrdineDettagliDTO> dtos = new ArrayList<>();
		originals.forEach(entity -> dtos.add(DTOMapper.toDTO(entity)));
		return dtos;
	}

	public Page<OrdineDettagliDTO> query(Pageable pageable) {
		return ordineDettagliRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private OrdineDettagli getByOrdineAndArticolo(Integer idOrdine, Integer idArticolo) {
		return ordineDettagliRepository.findByOrdineAndArticolo(idOrdine, idArticolo)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + idOrdine + ", " + idArticolo));
	}

	private List<OrdineDettagli> requireList(Integer id) {
		return ordineDettagliRepository.findByPOrdine(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public List<OrdineDettagli> test() {
		List<OrdineDettagli> dettagli = ordineDettagliRepository.findByPOrdine(902).get();
		Hibernate.initialize(dettagli);
		dettagli.forEach(d -> Hibernate.initialize(d.getArticolo()));
		return dettagli;
	}

}
