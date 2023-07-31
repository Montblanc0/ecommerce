package it.ecommerce.repository;

import it.ecommerce.model.Utente;
import it.ecommerce.model.UtenteAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer>, JpaSpecificationExecutor<Utente> {
	//public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	Optional<Utente> findByUsername(String username);


	@Query("SELECT DISTINCT u.id as id, u.username as username, u.password as password FROM Utente u WHERE u.username = :username")
	Optional<UtenteAuth> findByUsernameForAuthentication(@Param("username") String username);


	@Query("select DISTINCT u from Utente u")
	Page<Utente> findAllPage(Pageable pageable);

	//	@Query(value = "select distinct r.utenti from Ruolo r where r.id = :id")
	@Query("select distinct u from Ruolo r inner join r.utenti u where r.id = :id")
	Page<Utente> findAllByRole(Pageable pageable, @Param("id") Integer id);

	@Query("SELECT distinct u from Utente u where u.anagrafica.id = :id")
	Optional<Utente> findByAnagrafica(@Param("id") Integer id);

	@Query("SELECT distinct u from Utente u inner join Ordine o inner join OrdineNote n on n.id = o.id where o.cliente.id = u.id and n.id = :id")
	Optional<Utente> findByOrdineNote(@Param("id") Integer id);
}