package it.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "articoli")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Articolo implements Serializable {

	private static final long serialVersionUID = 4309166002835962821L;

	@Id
	@Column(name = "ID_ARTICOLO", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "p_marca", nullable = false, referencedColumnName = "ID_MARCA")
	private Marca marca;

	@Column(name = "ModelloArticolo", nullable = false)
	private String modello;

	@Column(name = "NomeArticolo", nullable = false)
	private String nome;

	@Column(name = "PrezzoArticolo", nullable = false)
	private BigDecimal prezzo;

	@Column(name = "ScontoPerUnita", nullable = false)
	private BigDecimal scontoPerUnita;

	//TODO: routine sottrai quantità
	@Column(name = "GiacenzaArticolo", nullable = false, columnDefinition = "SMALLINT", length = 5)
	private Integer giacenza;

	@Column(name = "GiacenzaMinArticolo", nullable = false, columnDefinition = "TINYINT", length = 3)
	private Integer giacenzaMinima;

	@Column(name = "DescrizioneArticolo", nullable = false)
	private String descrizione;

	//TODO: OrdineDettagliService toDTO LAZY?
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "articoli_sottocategorie",
			joinColumns = @JoinColumn(name = "p_Articolo"),
			inverseJoinColumns = @JoinColumn(name = "p_Sottocategoria"))
	@ToString.Exclude
	@JsonIgnoreProperties("categoria.sottocategorie")
	private Set<Sottocategoria> sottocategorie = new LinkedHashSet<>();

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Articolo articolo = (Articolo) o;
		return getId() != null && getId().equals(articolo.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
