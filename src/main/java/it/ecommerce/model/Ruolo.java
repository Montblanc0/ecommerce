package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.ecommerce.dto.RuoloDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@SqlResultSetMapping(
		name = "RuoloMapping",
		classes = @ConstructorResult(
				targetClass = RuoloDTO.class,
				columns = {
						@ColumnResult(name = "id", type = Integer.class),
						@ColumnResult(name = "ruolo", type = String.class)
				}
		)
)
@NamedNativeQuery(
		name = "Ruolo.findRuoloByIdUtente",
		query = "SELECT DISTINCT r.ID_RUOLO as id, r.ruolo as ruolo FROM `utenti_ruoli` ur JOIN ruoli r on ur.p_Ruolo = r.ID_RUOLO WHERE ur.p_Utente = :id",
		resultSetMapping = "RuoloMapping"
)
@RequiredArgsConstructor
@JsonIgnoreProperties("hibernateLazyInitializer")
@Table(name = "ruoli", schema = "e_commerce")
public class Ruolo implements Serializable {
	private static final long serialVersionUID = 2984837651991390307L;
	@Id
	@Column(name = "ID_RUOLO", nullable = false, columnDefinition = "TINYINT", length = 3)
	private Integer id;

	@Column(name = "ruolo", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleName ruolo;

	@ManyToMany(mappedBy = "ruoli")
	@ToString.Exclude
	@JsonIgnore
	private Set<Utente> utenti = new HashSet<>();

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Ruolo that = (Ruolo) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}