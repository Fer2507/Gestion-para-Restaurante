package itch.reservaciones.mapper;

import itch.reservaciones.dto.MesaDto;
import itch.reservaciones.entity.MesaEntity;

public class MesaMapper {
	public static MesaDto mapToMesaDto(MesaEntity mesaEntity) {
		return new MesaDto(
				mesaEntity.getIdMesa(),
				mesaEntity.getNumero(),
				mesaEntity.getCapacidad(),
				mesaEntity.getUbicacion(),
				mesaEntity.getEstado()
				);
	}
	public static MesaEntity mapToMesaEntity(MesaDto mesaDto) {
		return new MesaEntity(
				mesaDto.getIdMesa(),
				mesaDto.getNumero(),
				mesaDto.getCapacidad(),
				mesaDto.getUbicacion(),
				mesaDto.getEstado()
				);
	}
}
