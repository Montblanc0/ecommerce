package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ordini_dettagli", uniqueConstraints = @UniqueConstraint(columnNames = {"p_Ordine", "p_Articolo"}))
public class OrdineDettagli implements Serializable {

	private static final long serialVersionUID = -6543328083273345980L;

	@EmbeddedId
	private IdDettaglio id = new IdDettaglio();

	// Calculated by routine
	@Column(name = "TotaleArticolo", insertable = false)
	private BigDecimal totaleArticolo;

	@Column(name = "QuantitaArticolo", nullable = false, columnDefinition = "TINYINT", length = 3)
	private Integer quantita;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@MapsId("idOrdine")
	@JoinColumn(name = "p_Ordine", referencedColumnName = "ID_ORDINE", foreignKey = @ForeignKey(name = "ix_ordini_dettagli_p_ordine"))
	@JsonBackReference
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@ToString.Exclude
	private Ordine ordine;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@MapsId("idArticolo")
	@JoinColumn(name = "p_Articolo", referencedColumnName = "ID_ARTICOLO", foreignKey = @ForeignKey(name = "ix_ordini_dettagli_p_articolo"))
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@ToString.Exclude
	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Articolo articolo;


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		OrdineDettagli that = (OrdineDettagli) o;
		return getOrdine() != null && Objects.equals(getOrdine(), that.getOrdine())
				&& getArticolo() != null && Objects.equals(getArticolo(), that.getArticolo());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(ordine, articolo);
	}
}

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
class IdDettaglio implements Serializable {
	private static final long serialVersionUID = -5726905253099203403L;
	@Column(name = "p_Ordine", nullable = false)
	private Integer idOrdine;
	@Column(name = "p_Articolo", nullable = false)
	private Integer idArticolo;
}
