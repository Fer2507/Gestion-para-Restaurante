package com.delacruz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delacruz.dto.ClienteDto;
import com.delacruz.service.ClienteService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear Cliente
    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        return new ResponseEntity<>(clienteService.createCliente(clienteDto), HttpStatus.CREATED);
    }

    // Obtener cliente por ID
    @PreAuthorize("hasAuthority('GESTIONAR_CLIENTES') or hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    // Obtener todos los clientes
    @PreAuthorize("hasAuthority('LISTAR_CLIENTES') or hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    // Actualizar Cliente
    @PreAuthorize("hasAuthority('GESTIONAR_CLIENTES') or hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Integer id, @RequestBody ClienteDto updateClienteDto) {
        return ResponseEntity.ok(clienteService.updateCliente(id, updateClienteDto));
    }

    // Eliminar Cliente
    @PreAuthorize("hasAuthority('DAR_BAJA_CLIENTE') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok("Registro eliminado");
    }

    // Buscar por nombre
    @PreAuthorize("hasAuthority('LISTAR_CLIENTES') or hasRole('ADMINISTRADOR') or hasRole('MESERO')")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDto>> buscarCliente(@RequestParam String nombreCliente) {
        return ResponseEntity.ok(clienteService.buscarClientesPorNombre(nombreCliente));
    }
}
