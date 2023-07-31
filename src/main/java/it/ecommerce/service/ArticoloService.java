package it.ecommerce.service;

import it.ecommerce.dto.ArticoloDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Articolo;
import it.ecommerce.repository.ArticoloRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticoloService extends AbstractService<Integer, ArticoloDTO> {

	@Autowired
	private ArticoloRepository articoloRepository;

	@Autowired
	private SottocategoriaService sottocategoriaService;

	public Integer save(ArticoloDTO dto) {
		Articolo bean = new Articolo();
		BeanUtils.copyProperties(dto, bean);
		bean = articoloRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		articoloRepository.deleteById(id);
	}

	public void update(Integer id, ArticoloDTO dto) {
		Articolo bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		articoloRepository.save(bean);
	}


	public ArticoloDTO getById(Integer id) {
		Articolo original = requireOne(id);
		ArticoloDTO dto = DTOMapper.toDTO(original);
		return dto;
	}

	public Page<ArticoloDTO> query(Pageable pageable) {
		return articoloRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private Articolo requireOne(Integer id) {
		return articoloRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Articolo test() {
		Articolo articolo = articoloRepository.getById(16);
		Hibernate.initialize(articolo);
		return articolo;
	}
}
