package it.ecommerce.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrdineNoteDTO implements Serializable {
	private static final long serialVersionUID = 3523344913814179311L;

	private Integer id;

	private String nota;
}
