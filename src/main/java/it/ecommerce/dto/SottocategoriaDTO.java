package it.ecommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SottocategoriaDTO implements Serializable {
	private static final long serialVersionUID = 2844597808544625143L;

	private Integer id;

	@JsonIgnoreProperties("sottocategorie")
	private CategoriaDTO categoria;

	private String sottocategoria;
}
