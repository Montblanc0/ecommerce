package it.ecommerce.service;

import it.ecommerce.dto.OrdineStatoDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.OrdineStato;
import it.ecommerce.repository.OrdineStatoRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class OrdineStatoService extends AbstractService<Integer, OrdineStatoDTO> {

	@Autowired
	private OrdineStatoRepository ordineStatoRepository;

	public Integer save(OrdineStatoDTO dto) {
		OrdineStato bean = new OrdineStato();
		BeanUtils.copyProperties(dto, bean);
		bean = ordineStatoRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		ordineStatoRepository.deleteById(id);
	}

	public void update(Integer id, OrdineStatoDTO dto) {
		OrdineStato bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		ordineStatoRepository.save(bean);
	}

	public OrdineStatoDTO getById(Integer id) {
		OrdineStato original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<OrdineStatoDTO> query(Pageable pageable) {
		return ordineStatoRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}

	private OrdineStato requireOne(Integer id) {
		return ordineStatoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	// TEST
	public OrdineStato test() {
		OrdineStato stato = ordineStatoRepository.getById(50);
		Hibernate.initialize(stato);
		return stato;
	}
}
