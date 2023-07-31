package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "utenti", schema = "e_commerce", indexes = {
		@Index(name = "utente__username", columnList = "username", unique = true),
		@Index(name = "utente__p_Anagrafica", columnList = "p_Anagrafica", unique = true)
})
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Utente implements Serializable {
	private static final long serialVersionUID = -2010359481941867029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UTENTE", nullable = false)
	private Integer id;
	@Column(name = "username", nullable = false, unique = true, length = 36)
	private String username;
	@Column(name = "password", nullable = false, columnDefinition = "CHAR", length = 60)
	private String password;
	@OneToOne
	@JoinColumn(name = "p_Anagrafica", nullable = false, referencedColumnName = "ID_ANAGRAFICA")
	private Anagrafica anagrafica;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "utenti_ruoli",
			joinColumns = {@JoinColumn(name = "p_Utente", referencedColumnName = "ID_UTENTE")},
			inverseJoinColumns = {@JoinColumn(name = "p_Ruolo", referencedColumnName = "ID_RUOLO")},
			uniqueConstraints = @UniqueConstraint(columnNames = {"p_Utente", "p_Ruolo"}))
	private Set<Ruolo> ruoli = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "utenti_indirizzi",
			joinColumns = {@JoinColumn(name = "p_Utente", referencedColumnName = "ID_UTENTE")},
			inverseJoinColumns = {@JoinColumn(name = "p_Indirizzo", referencedColumnName = "ID_INDIRIZZO")},
			uniqueConstraints = @UniqueConstraint(columnNames = {"p_Utente", "p_Indirizzo"}, name = "ix_Utenti_Indirizzi__p_Utente__p__Indirizzo"))
	@ToString.Exclude
	private Set<Indirizzo> indirizzi = new HashSet<>();

	@OneToMany(mappedBy = "cliente")
	@ToString.Exclude
	@JsonBackReference
	private Set<Ordine> ordini = new LinkedHashSet<>();

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Utente utente = (Utente) o;
		return getId() != null && Objects.equals(getId(), utente.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}