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
@Table(name = "art_marche")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Marca implements Serializable {

	private static final long serialVersionUID = 8840773246784097429L;

	@Id
	@Column(name = "ID_MARCA", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "Marca", nullable = false)
	private String marca;

	@OneToMany(mappedBy = "marca")
	@JsonIgnore
	@ToString.Exclude
	private Set<Articolo> articoli = new LinkedHashSet<>();


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Marca marca = (Marca) o;
		return getId() != null && getId().equals(marca.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
