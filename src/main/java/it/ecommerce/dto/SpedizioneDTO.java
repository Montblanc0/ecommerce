package it.ecommerce.dto;


import it.ecommerce.model.SpedizioneTipo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SpedizioneDTO implements Serializable {
	private static final long serialVersionUID = -8531070192434890843L;

	private Integer id;

	private SpedizioneTipo spedizione;

	private BigDecimal prezzo;
}
