package it.ecommerce.repository;

import it.ecommerce.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdineRepository extends JpaRepository<Ordine, Integer>, JpaSpecificationExecutor<Ordine> {
	@Query(value = "select o from Ordine o")
	Page<Ordine> findAllPage(Pageable pageable);

	@Query(value = "select o from Ordine o where o.cliente.id = :id")
	Page<Ordine> findAllByUtente(Pageable pageable, @Param("id") Integer id);
}