package it.ecommerce.dto;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RuoloDTO implements Serializable, GrantedAuthority {
	private static final long serialVersionUID = 1751546644331654627L;

	private Integer id;

	private String ruolo;

	@Override
	public String getAuthority() {
		return ruolo;
	}
}
