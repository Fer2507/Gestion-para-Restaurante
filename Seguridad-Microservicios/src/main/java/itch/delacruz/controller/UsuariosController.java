package itch.delacruz.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itch.delacruz.dto.UsuarioDto;
import itch.delacruz.service.UsuariosService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuariosController {

    @Autowired
    private UsuariosService usuarioService;
    
    @PostMapping("/crear")
    public UsuarioDto crearUsuario(@RequestBody UsuarioDto dto) {
    	return usuarioService.crearUsuario(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public UsuarioDto obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<UsuarioDto> listar() {
        return usuarioService.listarUsuarios();
    }
    
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public UsuarioDto actualizar(@PathVariable Integer id, @RequestBody UsuarioDto dto) {
    	return usuarioService.actualizarUsuario(id, dto);
    }
}
