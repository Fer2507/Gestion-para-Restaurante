package itch.reservaciones.mapper;

import itch.reservaciones.dto.EmpleadoDto;
import itch.reservaciones.entity.EmpleadoEntity;

public class EmpleadoMapper {
	public static EmpleadoDto mapToEmpleadoDto(EmpleadoEntity empleadoEntity) {
		return new EmpleadoDto(
				empleadoEntity.getIdEmpleado(),
				empleadoEntity.getNombreEmp(),
				empleadoEntity.getPuesto(),
				empleadoEntity.getClave(),
				empleadoEntity.getIdUsuario(),
				empleadoEntity.getEmail()
				);
	}
	public static EmpleadoEntity mapToEmpleadoEntity(EmpleadoDto empleadoDto) {
		return new EmpleadoEntity(
				empleadoDto.getIdEmpleado(),
				empleadoDto.getNombreEmp(),
				empleadoDto.getPuesto(),
				empleadoDto.getClave(),
				empleadoDto.getIdUsuario(),
				empleadoDto.getEmail()
				);
	}

}
