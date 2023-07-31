package it.ecommerce.service;

import it.ecommerce.dto.IndirizzoDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Indirizzo;
import it.ecommerce.repository.IndirizzoRepository;
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
public class IndirizzoService {

	@Autowired
	private IndirizzoRepository indirizzoRepository;

	public Integer save(IndirizzoDTO dto) {
		Indirizzo bean = new Indirizzo();
		BeanUtils.copyProperties(dto, bean);
		bean = indirizzoRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		indirizzoRepository.deleteById(id);
	}

	public void update(Integer id, IndirizzoDTO dto) {
		Indirizzo bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		indirizzoRepository.save(bean);
	}

	public IndirizzoDTO getById(Integer id) {
		Indirizzo original = requireOne(id);
		return DTOMapper.toDTO(original);
	}

	public Page<IndirizzoDTO> query(Pageable pageable) {
		return indirizzoRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}

	private Indirizzo requireOne(Integer id) {
		return indirizzoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Indirizzo test() {
		Indirizzo indirizzo = indirizzoRepository.getById(22);
		Hibernate.initialize(indirizzo);
		return indirizzo;
	}
}
