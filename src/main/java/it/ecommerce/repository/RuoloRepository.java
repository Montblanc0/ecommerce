package it.ecommerce.repository;

import it.ecommerce.dto.RuoloDTO;
import it.ecommerce.model.Ruolo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RuoloRepository extends JpaRepository<Ruolo, Integer>, JpaSpecificationExecutor<Ruolo> {
	@Query(value = "select r from Ruolo r")
	Page<Ruolo> findAllPage(Pageable pageable);

	@Query(name = "Ruolo.findRuoloByIdUtente", nativeQuery = true)
	Optional<Set<RuoloDTO>> findRuoloByIdUtente(@Param("id") Integer id);
	
}