package itch.fonda.service;

import java.util.List;

import itch.fonda.dto.TipoDto;

public interface TipoService {
	
	TipoDto createTipo (TipoDto tipoDto);
	
	//BUSCAR Tipo POR ID
	TipoDto getTipoById(Integer Id);
	
	//OBTENER TODOS LOS Tipo
	List<TipoDto> getAllTipo();
	
	//CONTRUIR REST API UPDATE Tipo
	TipoDto updateTipo(Integer Id, TipoDto updateTipo);
	
	//CONSTRUIR DELETE REST API Tipo
	void deleteTipo(Integer Id); 

}
