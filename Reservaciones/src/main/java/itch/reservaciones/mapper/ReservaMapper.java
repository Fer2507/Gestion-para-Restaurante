package itch.reservaciones.mapper;

import java.util.ArrayList;
import java.util.List;

import itch.reservaciones.dto.ReservaDto;
import itch.reservaciones.dto.VentaDto;
import itch.reservaciones.entity.MesaEntity;
import itch.reservaciones.entity.ReservaEntity;

public class ReservaMapper {
    public static ReservaDto mapToReservaDto(ReservaEntity reservaEntity, String nombreCliente, List<VentaDto> ventas) {
        if (reservaEntity == null) return null;

        String descripcionMesa = reservaEntity.getIdMesa() != null 
                ? reservaEntity.getIdMesa().getUbicacion() 
                : null;
        
        if(ventas == null) ventas = new ArrayList<>();

        return new ReservaDto(
            reservaEntity.getIdReserva(),
            reservaEntity.getIdMesa() != null ? reservaEntity.getIdMesa().getIdMesa() : null,
            reservaEntity.getIdCliente(),
            reservaEntity.getFechaReserva(),
            ventas,
            reservaEntity.getEstatus(),
            nombreCliente,
            descripcionMesa
        );
    }

    public static ReservaEntity mapToReservaEntity(ReservaDto reservaDto, MesaEntity mesaEntity) {
        if (reservaDto == null) return null;

        return new ReservaEntity(
            reservaDto.getIdReserva(),
            mesaEntity,
            reservaDto.getIdCliente(),
            reservaDto.getFechaReserva(),
            reservaDto.getEstatus()
        );
    }
}
