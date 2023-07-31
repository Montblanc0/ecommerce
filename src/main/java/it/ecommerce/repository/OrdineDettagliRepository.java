package it.ecommerce.repository;

import it.ecommerce.model.OrdineDettagli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdineDettagliRepository extends JpaRepository<OrdineDettagli, Void>, JpaSpecificationExecutor<OrdineDettagli> {
	void deleteByOrdine(Integer id);

	@Query("select d from OrdineDettagli d where d.ordine.id = :idOrdine and d.articolo.id = :idArticolo")
	Optional<OrdineDettagli> findByOrdineAndArticolo(@Param("idOrdine") Integer idOrdine, @Param("idArticolo") Integer idArticolo);

	Optional<List<OrdineDettagli>> findByOrdine(Integer id);

	@Query(value = "select o from OrdineDettagli o")
	Page<OrdineDettagli> findAllPage(Pageable pageable);

	@Query(value = "select o from OrdineDettagli o where o.ordine.id = :id")
	Optional<List<OrdineDettagli>> findByPOrdine(@Param("id") Integer id);

//	Optional<OrdineDettagli> findByIdOrdineAndIdArticolo(Integer idOrdine, Integer idArticolo);
}