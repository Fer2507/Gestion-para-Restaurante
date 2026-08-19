package com.delacruz.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.delacruz.dto.ClienteDto;
import com.delacruz.dto.UsuarioDto;
import com.delacruz.entity.Cliente;
import com.delacruz.mapper.ClienteMapper;
import com.delacruz.repository.ClienteRepository;
import com.delacruz.segurity.SeguridadClient;
import com.delacruz.service.ClienteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
	
    private final SeguridadClient seguridadClient;

    private ClienteRepository clienteRepository;
    @Override
    public ClienteDto createCliente(ClienteDto clienteDto) {
        Cliente cliente = ClienteMapper.mapToCliente(clienteDto);
        Cliente savedCliente = clienteRepository.save(cliente);
       
        UsuarioDto usuario = new UsuarioDto();
        usuario.setUsername(savedCliente.getCorreoCliente());
        usuario.setPassword(savedCliente.getClave());
        usuario.setRol("CLIENTE");
        usuario.setEmail(savedCliente.getCorreoCliente());
        
        // Llamar al microservicio
        UsuarioDto usuarioCreado = seguridadClient.crearUsuario(usuario);

        //Guardar el ID del usuario dentro del Usuario
        savedCliente.setIdUsuario(usuarioCreado.getIdUsuario());
        clienteRepository.save(savedCliente);
        System.out.println("Cliente Creado Correctamente");
        
        return ClienteMapper.mapToClienteDto(savedCliente);
    }

    @Override
    public ClienteDto getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clientecon id: "+ id +" no emcontrado"));
        return ClienteMapper.mapToClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteMapper::mapToClienteDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto updateCliente(Integer id, ClienteDto updateClienteDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clientecon id: "+ id +" no emcontrado"));

        //ACTUALIZAR DATOS DEL CLIENTE
        cliente.setNombreCliente(updateClienteDto.getNombreCliente());
        cliente.setTelefonoCliente(updateClienteDto.getTelefonoCliente());
        cliente.setCorreoCliente(updateClienteDto.getCorreoCliente());
        cliente.setClave(updateClienteDto.getClave());
        
        // SI NO TIENE usuario, lo creamos
        if (cliente.getIdUsuario() == null) {

            UsuarioDto usuario = new UsuarioDto();
            usuario.setUsername(cliente.getNombreCliente());
            usuario.setPassword(cliente.getClave());
            usuario.setRol("CLIENTE");
            usuario.setEmail(cliente.getCorreoCliente());

            UsuarioDto usuarioCreado = seguridadClient.crearUsuario(usuario);

            cliente.setIdUsuario(usuarioCreado.getIdUsuario());
        }  else {
            // Ya tiene usuario -> actualizarlo
        	UsuarioDto usuarioActualizado = new UsuarioDto();
            usuarioActualizado.setIdUsuario(cliente.getIdUsuario());
            usuarioActualizado.setUsername(updateClienteDto.getNombreCliente());
            usuarioActualizado.setPassword(updateClienteDto.getClave());
            usuarioActualizado.setEmail(updateClienteDto.getCorreoCliente());
            usuarioActualizado.setRol("CLIENTE");

            // Llamar microservicio de seguridad para actualizar
            seguridadClient.actualizarUsuario(cliente.getIdUsuario(), usuarioActualizado);
        }

        // guardar cambios
        Cliente updatedCliente = clienteRepository.save(cliente);
        
        return ClienteMapper.mapToClienteDto(updatedCliente);
    }
     
    @Override
    public void deleteCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clientecon id: "+ id +" no emcontrado"));
        clienteRepository.delete(cliente);
    }
    
    @Override
    public List<ClienteDto> buscarClientesPorNombre(String nombreCliente) {
        List<Cliente> clientes = clienteRepository.findByNombreClienteContainingIgnoreCase(nombreCliente);
        return clientes.stream()
                .map(ClienteMapper::mapToClienteDto)
                .collect(Collectors.toList());
    }
}
