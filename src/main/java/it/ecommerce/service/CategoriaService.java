package it.ecommerce.service;

import it.ecommerce.dto.CategoriaDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Categoria;
import it.ecommerce.repository.CategoriaRepository;
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
public class CategoriaService extends AbstractService<Integer, CategoriaDTO> {

	@Autowired
	private CategoriaRepository categoriaRepository;


	public Integer save(CategoriaDTO dto) {
		Categoria bean = new Categoria();
		BeanUtils.copyProperties(dto, bean);
		bean = categoriaRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		categoriaRepository.deleteById(id);
	}

	public void update(Integer id, CategoriaDTO dto) {
		Categoria bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		categoriaRepository.save(bean);
	}

	public CategoriaDTO getById(Integer id) {
		Categoria original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<CategoriaDTO> query(Pageable pageable) {
		return categoriaRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private Categoria requireOne(Integer id) {
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Categoria test() {
		Categoria categoria = categoriaRepository.getById(22);
		Hibernate.initialize(categoria);
		Hibernate.initialize(categoria.getSottocategorie());
		return categoria;
	}
}
