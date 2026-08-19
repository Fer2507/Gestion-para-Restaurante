package com.delacruz.mapper;

import com.delacruz.dto.ClienteDto;
import com.delacruz.entity.Cliente;

public class ClienteMapper {

    public static ClienteDto mapToClienteDto(Cliente cliente) {
        return new ClienteDto(
            cliente.getIdCliente(),
            cliente.getNombreCliente(),
            cliente.getCorreoCliente(),   
            cliente.getTelefonoCliente(),
            cliente.getClave(),
            cliente.getIdUsuario()
        );
    }

    public static Cliente mapToCliente(ClienteDto clienteDto) {
        return new Cliente(
            clienteDto.getIdCliente(),
            clienteDto.getNombreCliente(),
            clienteDto.getTelefonoCliente(), 
            clienteDto.getCorreoCliente(),
            clienteDto.getClave(),
            clienteDto.getIdUsuario()
        );
    } 
}
