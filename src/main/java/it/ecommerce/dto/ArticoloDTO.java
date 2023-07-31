package it.ecommerce.dto;


import it.ecommerce.model.Marca;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ArticoloDTO implements Serializable {
	private static final long serialVersionUID = 6087050715898161786L;

	private Integer id;

	private Marca marca;

	private Set<SottocategoriaDTO> sottocategorie;

	private String modello;

	private String nome;

	private BigDecimal prezzo;

	private BigDecimal scontoPerUnita;

	private Integer giacenza;

	private Integer giacenzaMinima;

	private String descrizione;
}
