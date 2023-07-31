package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ord_note")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class OrdineNote implements Serializable {

	private static final long serialVersionUID = 3098262408272021679L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ordine")
	@GenericGenerator(name = "ordine", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "ordine"))
	@Column(name = "p_Ordine", nullable = false)
	private Integer id;

	@Column(name = "NotaOrdine", nullable = false)
	private String nota;

	@MapsId("id")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "p_Ordine_Note", referencedColumnName = "ID_ORDINE")
	@ToString.Exclude
	@JsonIgnore
	private Ordine ordine;


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		OrdineNote ordineNote = (OrdineNote) o;
		return id != null && id.equals(ordineNote.id);
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
