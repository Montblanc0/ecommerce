package it.ecommerce.service;

import it.ecommerce.dto.RuoloDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Ruolo;
import it.ecommerce.repository.RuoloRepository;
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
public class RuoloService extends AbstractService<Integer, RuoloDTO> {

	@Autowired
	private RuoloRepository ruoloRepository;


	public Integer save(RuoloDTO dto) {
		Ruolo bean = new Ruolo();
		BeanUtils.copyProperties(dto, bean);
		bean = ruoloRepository.save(bean);
		return bean.getId();
	}


	public void delete(Integer id) {
		ruoloRepository.deleteById(id);
	}

	public void update(Integer id, RuoloDTO dto) {
		Ruolo bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		ruoloRepository.save(bean);
	}

	public RuoloDTO getById(Integer id) {
		Ruolo original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<RuoloDTO> query(Pageable pageable) {
		return ruoloRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}
	

	private Ruolo requireOne(Integer id) {
		return ruoloRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST

	public Ruolo test() {
		Ruolo ruolo = ruoloRepository.getById(1);
		Hibernate.initialize(ruolo);
		return ruolo;
	}


}
