package it.ecommerce.repository;

import it.ecommerce.model.Anagrafica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AnagraficaRepository extends JpaRepository<Anagrafica, Integer>, JpaSpecificationExecutor<Anagrafica> {
	@Query(value = "select a from Anagrafica a")
	Page<Anagrafica> findAllPage(Pageable pageable);
}