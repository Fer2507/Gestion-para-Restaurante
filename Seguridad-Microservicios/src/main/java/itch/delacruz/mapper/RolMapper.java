package itch.delacruz.mapper;

import java.util.stream.Collectors;

import itch.delacruz.dto.RolDto;
import itch.delacruz.entity.Rol;

public class RolMapper {
	public static RolDto mapToRolDto(Rol rol) {
		return new RolDto(
				rol.getIdRol(),
				rol.getNombre(),
				rol.getDescripcion(),
				rol.getPermisos() != null
                ? rol.getPermisos()
                    .stream()
                    .map(PermisoMapper::mapToRolDto)
                    .collect(Collectors.toSet())
                : null
				);
	}
	
	public static Rol mapToRol(RolDto rolDto) {
		return new Rol(
				rolDto.getIdRol(),
				rolDto.getNombre(),
				rolDto.getDescripcion(),
				 rolDto.getPermisos() != null
	                ? rolDto.getPermisos()
	                    .stream()
	                    .map(PermisoMapper::mapToRol)
	                    .collect(Collectors.toSet())
	                : null
				);
	}
}
