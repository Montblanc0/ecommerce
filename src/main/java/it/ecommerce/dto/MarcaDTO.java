package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MarcaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String marca;
}
