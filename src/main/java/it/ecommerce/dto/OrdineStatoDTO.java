package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrdineStatoDTO implements Serializable {
	private static final long serialVersionUID = -5990129852828127603L;

	private Integer id;

	private String stato;
}
