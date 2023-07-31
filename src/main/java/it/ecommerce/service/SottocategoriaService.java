package it.ecommerce.service;

import it.ecommerce.dto.SottocategoriaDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Sottocategoria;
import it.ecommerce.repository.SottocategoriaRepository;
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
public class SottocategoriaService extends AbstractService<Integer, SottocategoriaDTO> {

	@Autowired
	private SottocategoriaRepository sottocategoriaRepository;

	public Integer save(SottocategoriaDTO dto) {
		Sottocategoria bean = new Sottocategoria();
		BeanUtils.copyProperties(dto, bean);
		bean = sottocategoriaRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		sottocategoriaRepository.deleteById(id);
	}

	public void update(Integer id, SottocategoriaDTO dto) {
		Sottocategoria bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		sottocategoriaRepository.save(bean);
	}

	public SottocategoriaDTO getById(Integer id) {
		Sottocategoria original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<SottocategoriaDTO> query(Pageable pageable) {
		return sottocategoriaRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}

	private Sottocategoria requireOne(Integer id) {
		return sottocategoriaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Sottocategoria test() {
		Sottocategoria sottocategoria = sottocategoriaRepository.getById(1);
		Hibernate.initialize(sottocategoria);
		return sottocategoria;
	}
}
