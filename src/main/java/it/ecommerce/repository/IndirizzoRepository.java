package it.ecommerce.repository;

import it.ecommerce.model.Indirizzo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Integer>, JpaSpecificationExecutor<Indirizzo> {
	@Query(value = "select i from Indirizzo i")
	Page<Indirizzo> findAllPage(Pageable pageable);
}