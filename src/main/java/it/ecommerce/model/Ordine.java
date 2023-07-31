package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "ordini", uniqueConstraints = @UniqueConstraint(columnNames = {"NumeroOrdine", "AnnoOrdine"}))
public class Ordine implements Serializable {

	private static final long serialVersionUID = -2084839740289085104L;

	@Id
	@Column(name = "ID_ORDINE", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//TODO: should be sent as null, it's calculated by routine
//	@Column(name = "NumeroOrdine", nullable = false, insertable = false, columnDefinition = "MEDIUMINT", length = 6)
	@Column(name = "NumeroOrdine", insertable = false, columnDefinition = "MEDIUMINT", length = 6)
	private Integer numero;

	//	If you don't want the cliente field to be persisted as a
//	new entity, you can use a @ManyToOne association without
//	cascading PERSIST or ALL operations. By default, the
//	@ManyToOne association does not cascade any operations,
//	so you can simply set the Utente entity as the value of the
//	cliente field without worrying about it being persisted as
//	a new entity.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "p_Cliente", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties({"ruoli", "indirizzi", "ordini", "hibernateLazyInitializer"})
	@ToString.Exclude
	private Utente cliente;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "p_Indirizzo", referencedColumnName = "ID_INDIRIZZO", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@ToString.Exclude
	private Indirizzo indirizzo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "p_Spedizione", referencedColumnName = "ID_SPEDIZIONE", nullable = false)
	@ToString.Exclude
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private Spedizione spedizione;

	// Gets initialized as 0
	@ManyToOne(optional = false, targetEntity = OrdineStato.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "p_StatoOrdine", nullable = false, insertable = false, referencedColumnName = "ID_STATO")
	@ToString.Exclude
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private OrdineStato stato;

	//TODO: should be sent as null, it's calculated by routine
//	@Column(name = "AnnoOrdine", nullable = false, insertable = false, columnDefinition = "YEAR", length = 4)
	@Column(name = "AnnoOrdine", insertable = false, columnDefinition = "YEAR", length = 4)
	private Integer anno;

	//TODO: should be sent as null, it's calculated by routine
	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "DataOrdine", nullable = false, insertable = false)
	@Column(name = "DataOrdine", insertable = false)
	private Date data;

	//TODO: should be sent as null, it's calculated by routine
//	@Column(name = "Sconto", nullable = false, insertable = false)
	@Column(name = "Sconto", insertable = false)
	private BigDecimal sconto = BigDecimal.ZERO;

	//TODO: should be sent as null, it's calculated by routine
	// hibernate complains does not have default value
	// Gets initialized as 0.
//	@Column(name = "TotaleOrdine", nullable = false, insertable = false)
	@Column(name = "TotaleOrdine", insertable = false)
	private BigDecimal totale;

	@OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "ordine")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private OrdineNote note;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "ordine")
	@ToString.Exclude
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private Set<OrdineDettagli> dettagli = new LinkedHashSet<>();


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Ordine ordine = (Ordine) o;
		return getId() != null && getId().equals(ordine.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
