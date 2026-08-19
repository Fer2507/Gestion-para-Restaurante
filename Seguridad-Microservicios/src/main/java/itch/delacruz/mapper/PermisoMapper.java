package itch.delacruz.mapper;

import itch.delacruz.dto.PermisoDto;
import itch.delacruz.entity.Permiso;

public class PermisoMapper {
	
	public static PermisoDto mapToRolDto(Permiso permiso) {
		return new PermisoDto(
				permiso.getIdPermiso(),
				permiso.getNombre(),
				permiso.getDescripcion()
				);
	}
	
	public static Permiso mapToRol(PermisoDto permisoDto) {
		return new Permiso(
				permisoDto.getIdPermiso(),
				permisoDto.getNombre(),
				permisoDto.getDescripcion()
				);
	}

}
