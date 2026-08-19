package itch.delacruz.mapper;

import itch.delacruz.dto.UsuarioDto;
import itch.delacruz.entity.Usuario;

public class UsuarioMapper {
	public static UsuarioDto mapToUsuariosDto(Usuario usuario) {
		return new UsuarioDto(
				usuario.getIdUsuario(),
				usuario.getUsername(),
				usuario.getPassword(),
				usuario.getEmail(),
				usuario.getEstado(),
				usuario.getRol().getNombre()
				);
	}
	
	public static Usuario mapToUsuarios(UsuarioDto usuarioDto) {
		return new Usuario(
				usuarioDto.getIdUsuario(),
				usuarioDto.getUsername(),
				usuarioDto.getPassword(),
				usuarioDto.getEmail(),
				usuarioDto.getEstado(),
				null
				);
	}
	
}
