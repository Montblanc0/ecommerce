package it.ecommerce.model;

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
@Table(name = "indirizzi")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Indirizzo implements Serializable {

	private static final long serialVersionUID = -1159912835885676441L;

	@Id
	@Column(name = "ID_INDIRIZZO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "indirizzo", nullable = false, length = 60)
	private String indirizzo;

	@Column(name = "indirizzo_secondario", length = 60)
	private String indirizzoSecondario;

	@Column(name = "cap", nullable = false, columnDefinition = "CHAR", length = 5)
	private String cap;

	@Column(name = "citta", nullable = false, length = 40)
	private String citta;

	@Column(name = "provincia", nullable = false, columnDefinition = "CHAR", length = 2)
	private String provincia;

	@Column(name = "note", length = 300)
	private String note;

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Indirizzo indirizzo = (Indirizzo) o;
		return getId() != null && Objects.equals(getId(), indirizzo.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
