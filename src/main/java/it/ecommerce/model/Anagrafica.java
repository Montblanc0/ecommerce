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
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "anagrafica")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Anagrafica implements Serializable {

	private static final long serialVersionUID = -3347073714451547144L;

	@Id
	@Column(name = "ID_ANAGRAFICA", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "RagioneSociale")
	private String ragioneSociale;

	@Column(name = "Cognome")
	private String cognome;

	@Column(name = "Nome")
	private String nome;

	@Column(name = "Email", nullable = false)
	private String email;

	@Column(name = "Telefono", nullable = false)
	private String telefono;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "Indirizzo", nullable = false)
	private String indirizzo;

	@Column(name = "CAP", nullable = false, columnDefinition = "CHAR", length = 5)
	private String cap;

	@Column(name = "Citta", nullable = false)
	private String citta;

	@Column(name = "Provincia", nullable = false, columnDefinition = "CHAR", length = 2)
	private String provincia;

	@Column(name = "Note")
	private String note;

	@OneToOne(mappedBy = "anagrafica")
	@JsonIgnore
	@ToString.Exclude
	private Utente utente;

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Anagrafica that = (Anagrafica) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
