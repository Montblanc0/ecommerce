package it.ecommerce.repository;

import it.ecommerce.model.OrdineStato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrdineStatoRepository extends JpaRepository<OrdineStato, Integer>, JpaSpecificationExecutor<OrdineStato> {
	@Query(value = "select o from OrdineStato o")
	Page<OrdineStato> findAllPage(Pageable pageable);
}