package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 5473303699915111993L;

	private Integer id;

	private String categoria;

	private Set<SottocategoriaDTO> sottocategorie;
}
