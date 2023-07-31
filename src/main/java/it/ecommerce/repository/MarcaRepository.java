package it.ecommerce.repository;

import it.ecommerce.model.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MarcaRepository extends JpaRepository<Marca, Integer>, JpaSpecificationExecutor<Marca> {
	@Query(value = "select m from Marca m")
	Page<Marca> findAllPage(Pageable pageable);
}