package it.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class AbstractService<S extends Number, T> {

	abstract Integer save(T dto);

	abstract void delete(S id);

	abstract void update(S id, T dto);

	abstract T getById(S id);

	abstract Page<T> query(Pageable p);
	
}
