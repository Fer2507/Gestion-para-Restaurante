package com.delacruz.service;

import java.util.List;
import com.delacruz.dto.ClienteDto;

public interface ClienteService {
	
	ClienteDto createCliente (ClienteDto clienteDto);
	
	//BUSCAR CLIENTE POR ID
	ClienteDto getClienteById(Integer Id);
	
	//OBTENER TODOS LOS CLIENTES
	List<ClienteDto> getAllClientes();
	
	//CONTRUIR REST API UPDATE CLIENTE
	ClienteDto updateCliente(Integer Id, ClienteDto updateCliente);
	
	//CONSTRUIR DELETE REST API Cliente
	void deleteCliente(Integer Id); 
	
	List<ClienteDto> buscarClientesPorNombre(String nombreCliente);
}
