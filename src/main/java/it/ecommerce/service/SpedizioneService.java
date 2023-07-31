package it.ecommerce.service;

import it.ecommerce.dto.SpedizioneDTO;
import it.ecommerce.dto.mapper.DTOMapper;
import it.ecommerce.model.Spedizione;
import it.ecommerce.repository.SpedizioneRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static it.ecommerce.dto.mapper.DTOMapper.toDTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class SpedizioneService {

	@Autowired
	private SpedizioneRepository spedizioneRepository;

	public Integer save(SpedizioneDTO dto) {
		Spedizione bean = new Spedizione();
		BeanUtils.copyProperties(dto, bean);
		bean = spedizioneRepository.save(bean);
		return bean.getId();
	}

	public void delete(Integer id) {
		spedizioneRepository.deleteById(id);
	}

	public void update(Integer id, SpedizioneDTO dto) {
		Spedizione bean = requireOne(id);
		BeanUtils.copyProperties(dto, bean);
		spedizioneRepository.save(bean);
	}

	public SpedizioneDTO getById(Integer id) {
		Spedizione original = requireOne(id);
		return toDTO(original);
	}

	public Page<SpedizioneDTO> query(Pageable pageable) {
		return spedizioneRepository.findAllPage(pageable).map(DTOMapper::toDTO);
	}


	private Spedizione requireOne(Integer id) {
		return spedizioneRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	//TEST
	public Spedizione test() {
		Spedizione spedizione = spedizioneRepository.getById(1);
		Hibernate.initialize(spedizione);
		return spedizione;
	}
}
