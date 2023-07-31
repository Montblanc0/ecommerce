package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class IndirizzoDTO implements Serializable {
	private static final long serialVersionUID = 2683067921764092246L;

	private Integer id;

	private String indirizzo;

	private String indirizzoSecondario;

	private String cap;

	private String citta;

	private String provincia;

	private String note;
}
