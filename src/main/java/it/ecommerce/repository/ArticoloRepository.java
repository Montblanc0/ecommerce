package it.ecommerce.repository;

import it.ecommerce.model.Articolo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ArticoloRepository extends JpaRepository<Articolo, Integer>, JpaSpecificationExecutor<Articolo> {

	@Query(value = "select a from Articolo a")
	Page<Articolo> findAllPage(Pageable pageable);
}