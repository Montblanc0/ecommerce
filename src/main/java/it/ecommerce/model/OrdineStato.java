package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ord_stati")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class OrdineStato implements Serializable {

	private static final long serialVersionUID = 3526706699758544471L;

	@Id
	@Column(name = "ID_STATO", nullable = false, columnDefinition = "TINYINT", length = 3)
	private Integer id;

	@Column(name = "StatoOrdine", nullable = false)
	private String stato;

	@OneToMany(mappedBy = "stato")
	@ToString.Exclude
	@JsonIgnore
	private Set<Ordine> ordini = new LinkedHashSet<>();


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		OrdineStato ordStati = (OrdineStato) o;
		return getId() != null && getId().equals(ordStati.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
