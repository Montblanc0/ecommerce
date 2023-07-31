package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginDTO implements Serializable {
	private static final long serialVersionUID = -4921730814372835488L;

	private String username;

	private String password;
}
