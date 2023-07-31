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
@Table(name = "sottocategorie", uniqueConstraints = @UniqueConstraint(columnNames = {"p_Categoria", "Sottocategoria"}))
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Sottocategoria implements Serializable {

	private static final long serialVersionUID = 5124829962134316102L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOTTOCATEGORIA", nullable = false)
	private Integer id;

	//TODO: OrdineDettagliService toDTO LAZY?
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "p_Categoria", nullable = false, referencedColumnName = "ID_CATEGORIA")
	@JsonIgnoreProperties("sottocategorie")
	private Categoria categoria;

	@Column(name = "Sottocategoria", nullable = false)
	private String sottocategoria;

	@ManyToMany
	@JoinTable(name = "articoli_sottocategorie",
			joinColumns = @JoinColumn(name = "p_Sottocategoria"),
			inverseJoinColumns = @JoinColumn(name = "p_Articolo"))
	@ToString.Exclude
	@JsonIgnore
	private Set<Articolo> articoli = new LinkedHashSet<>();


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Sottocategoria that = (Sottocategoria) o;
		return getId() != null && getId().equals(that.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
