package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrdineDTO implements Serializable {
	private static final long serialVersionUID = 1504074399249075577L;

	private Integer id;

	private Integer numero;

	private OrdineUtenteDTO cliente;

	private IndirizzoDTO indirizzo;

	private SpedizioneDTO spedizione;

	private OrdineStatoDTO stato;

	private Integer anno;

	private Date data;

	private BigDecimal sconto;

	private BigDecimal totale;

	private OrdineNoteDTO note;

	private Set<OrdineDettagliDTO> dettagli;
}
