package it.ecommerce.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrdineUtenteDTO implements Serializable {
	private static final long serialVersionUID = 1721479241945797696L;

	private Integer id;

	private String username;

	private AnagraficaDTO anagrafica;
}
