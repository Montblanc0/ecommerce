package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AnagraficaDTO implements Serializable {
	private static final long serialVersionUID = 6381367805120093646L;

	private Integer id;

	private String ragioneSociale;

	private String cognome;

	private String nome;

	private String email;

	private String telefono;

	private String mobile;

	private String indirizzo;

	private String cap;

	private String citta;

	private String provincia;

	private String note;
}
