package it.ecommerce.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrdineDettagliDTO implements Serializable {
	private static final long serialVersionUID = 4003793096426166374L;

	private Integer idOrdine;

	private Integer idArticolo;

	private BigDecimal totaleArticolo;

	private Integer quantita;

	private ArticoloDTO articolo;
}