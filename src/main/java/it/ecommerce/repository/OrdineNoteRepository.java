package it.ecommerce.repository;

import it.ecommerce.model.OrdineNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrdineNoteRepository extends JpaRepository<OrdineNote, Integer>, JpaSpecificationExecutor<OrdineNote> {
	@Query(value = "select o from OrdineNote o")
	Page<OrdineNote> findAllPage(Pageable pageable);
}