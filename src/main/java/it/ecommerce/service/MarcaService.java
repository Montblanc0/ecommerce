package it.ecommerce.service;

import it.ecommerce.dto.MarcaDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Marca;
import it.ecommerce.repository.MarcaRepository;
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
public class MarcaService extends AbstractService<Integer, MarcaDTO> {

	@Autowired
	private MarcaRepository marcaRepository;

	public Integer save(MarcaDTO dto) {
		Marca bean = new Marca();
		BeanUtils.copyProperties(dto, bean);
		bean = marcaRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		marcaRepository.deleteById(id);
	}

	public void update(Integer id, MarcaDTO dto) {
		Marca bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		marcaRepository.save(bean);
	}

	public MarcaDTO getById(Integer id) {
		Marca original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<MarcaDTO> query(Pageable pageable) {
		return marcaRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private Marca requireOne(Integer id) {
		return marcaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Marca test() {
		Marca marca = marcaRepository.getById(1);
		Hibernate.initialize(marca);
		return marca;
	}
}
