package it.ecommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UtenteDTO implements Serializable {
	private static final long serialVersionUID = -5420939030161374127L;

	private Integer id;

	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private AnagraficaDTO anagrafica;

	@JsonIgnoreProperties("authority")
	private Set<RuoloDTO> ruoli;

	private Set<IndirizzoDTO> indirizzi;
}
