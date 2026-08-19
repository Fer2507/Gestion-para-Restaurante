package itch.delacruz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import itch.delacruz.dto.UsuarioDto;
import itch.delacruz.entity.Rol;
import itch.delacruz.entity.Usuario;
import itch.delacruz.mapper.UsuarioMapper;
import itch.delacruz.repository.RolRepository;
import itch.delacruz.repository.UsuarioRepository;

@Service
public class UsuariosService {

    @Autowired private UsuarioRepository usuarioRepo;

    @Autowired  private RolRepository rolRepo;

    @Autowired  private PasswordEncoder passwordEncoder;

    /*Crear un nuevo usuario con su rol asignado. */
    public UsuarioDto crearUsuario(UsuarioDto dto) {
        // Buscar el rol usando el nombre
        Rol rol = rolRepo.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRol()));
        Usuario usuario = UsuarioMapper.mapToUsuarios(dto);

     // Encriptar contraseña y asignar rol
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(rol);
        usuario.setEstado(true);

        usuarioRepo.save(usuario);
        return UsuarioMapper.mapToUsuariosDto(usuario);
    }

    public UsuarioDto obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioMapper.mapToUsuariosDto(usuario);
    }
    
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepo.findAll().stream()
                .map(UsuarioMapper::mapToUsuariosDto)
                .toList();
    }
    
    public UsuarioDto actualizarUsuario(Integer id, UsuarioDto dto) {

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el rol usando el nombre
        Rol rol = rolRepo.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRol()));

        // Actualizar valores
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail()); 

        // Solo actualizar contraseña si se envía una nueva
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usuario.setRol(rol);
        usuarioRepo.save(usuario);
        return UsuarioMapper.mapToUsuariosDto(usuario);
    }
}
