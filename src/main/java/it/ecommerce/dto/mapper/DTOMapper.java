package it.ecommerce.dto.mapper;

import it.ecommerce.dto.*;
import it.ecommerce.model.*;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DTOMapper {
	public static AnagraficaDTO toDTO(Anagrafica original) {
		AnagraficaDTO dto = new AnagraficaDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	public static ArticoloDTO toDTO(Articolo original) {
		ArticoloDTO dto = new ArticoloDTO();
		BeanUtils.copyProperties(original, dto);
		dto.setSottocategorie(toDTO(original.getSottocategorie()));
		return dto;
	}


	public static Set<SottocategoriaDTO> toDTO(Set<Sottocategoria> sottocategorie) {
		Set<SottocategoriaDTO> dtos = new LinkedHashSet<>();
		for (Sottocategoria s : sottocategorie) {
			//TODO: aggiungi CategoriaDTO alla sottocategoria
			dtos.add(toDTO(s));
		}
		return dtos;
	}

	public static CategoriaDTO toDTO(Categoria original) {
		CategoriaDTO dto = new CategoriaDTO();
		Set<SottocategoriaDTO> sottocategorieDTO = new HashSet<>();
		original.getSottocategorie().forEach(sottocategoria -> {
			SottocategoriaDTO sottocategoriaDTO = new SottocategoriaDTO();
			BeanUtils.copyProperties(sottocategoria, sottocategoriaDTO);
			sottocategorieDTO.add(sottocategoriaDTO);
		});
		BeanUtils.copyProperties(original, dto);
		dto.setSottocategorie(sottocategorieDTO);
		return dto;
	}

	public static IndirizzoDTO toDTO(Indirizzo original) {
		IndirizzoDTO dto = new IndirizzoDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	public static MarcaDTO toDTO(Marca original) {
		MarcaDTO dto = new MarcaDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	//TODO
	public static OrdineDettagliDTO toDTO(OrdineDettagli original) {
		OrdineDettagliDTO dto = new OrdineDettagliDTO();
		BeanUtils.copyProperties(original, dto);
		dto.setIdOrdine(original.getOrdine().getId());
		dto.setIdArticolo(original.getArticolo().getId());
		dto.setArticolo(toDTO(original.getArticolo()));
		return dto;
	}

	public static OrdineNoteDTO toDTO(OrdineNote original) {
		OrdineNoteDTO dto = new OrdineNoteDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	public static OrdineDTO toDTO(Ordine original) {
		OrdineDTO dto = new OrdineDTO();
		BeanUtils.copyProperties(original, dto);
		Set<OrdineDettagli> dettagli = original.getDettagli();
		Set<OrdineDettagliDTO> dettagliDTO = new HashSet<>();
		dettagli.forEach(dettaglio -> dettagliDTO.add(toDTO(dettaglio)));
		dto.setDettagli(dettagliDTO);
		dto.setCliente(toOrdineUtenteDTO(original.getCliente()));
		dto.setIndirizzo(toDTO(original.getIndirizzo()));
		dto.setSpedizione(toDTO(original.getSpedizione()));
		dto.setStato(toDTO(original.getStato()));
		if (null != original.getNote()) {
			OrdineNoteDTO noteDTO = toDTO(original.getNote());
			dto.setNote(noteDTO);
		}
		return dto;
	}

	public static OrdineStatoDTO toDTO(OrdineStato original) {
		OrdineStatoDTO dto = new OrdineStatoDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	public static OrdineUtenteDTO toOrdineUtenteDTO(Utente original) {
		OrdineUtenteDTO dto = new OrdineUtenteDTO();
		BeanUtils.copyProperties(original, dto);
		dto.setAnagrafica(toDTO(original.getAnagrafica()));
		return dto;
	}

	public static RuoloDTO toDTO(Ruolo original) {
		RuoloDTO dto = new RuoloDTO();
		dto.setId(original.getId());
		dto.setRuolo(original.getRuolo().name());
		return dto;
	}

	public static SottocategoriaDTO toDTO(Sottocategoria original) {
		SottocategoriaDTO dto = new SottocategoriaDTO();
		BeanUtils.copyProperties(original, dto);
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		BeanUtils.copyProperties(original.getCategoria(), categoriaDTO);
		dto.setCategoria(categoriaDTO);
		return dto;
	}

	public static SpedizioneDTO toDTO(Spedizione original) {
		SpedizioneDTO dto = new SpedizioneDTO();
		BeanUtils.copyProperties(original, dto);
		return dto;
	}

	public static UtenteDTO toDTO(Utente original) {
		System.out.println("ORIGINAL: " + original);
		UtenteDTO utenteDTO = new UtenteDTO();
		BeanUtils.copyProperties(original, utenteDTO);
		Set<RuoloDTO> ruoliDTO = new HashSet<>();
		Set<IndirizzoDTO> indirizziDTO = new HashSet<>();
		original.getRuoli().forEach(ruolo -> {
			ruoliDTO.add(toDTO(ruolo));
		});
		original.getIndirizzi().forEach(indirizzo -> {
			indirizziDTO.add(toDTO(indirizzo));
		});
		utenteDTO.setAnagrafica(toDTO(original.getAnagrafica()));
		utenteDTO.setRuoli(ruoliDTO);
		utenteDTO.setIndirizzi(indirizziDTO);
		return utenteDTO;
	}
}
