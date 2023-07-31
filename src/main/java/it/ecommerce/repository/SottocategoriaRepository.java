package it.ecommerce.repository;

import it.ecommerce.model.Sottocategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SottocategoriaRepository extends JpaRepository<Sottocategoria, Integer>, JpaSpecificationExecutor<Sottocategoria> {
	@Query(value = "select s from Sottocategoria s")
	Page<Sottocategoria> findAllPage(Pageable pageable);
}