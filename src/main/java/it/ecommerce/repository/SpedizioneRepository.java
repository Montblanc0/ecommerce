package it.ecommerce.repository;

import it.ecommerce.model.Spedizione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SpedizioneRepository extends JpaRepository<Spedizione, Integer>, JpaSpecificationExecutor<Spedizione> {
	@Query(value = "select DISTINCT s from Spedizione s")
	Page<Spedizione> findAllPage(Pageable pageable);
}