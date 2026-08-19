package itch.reservaciones.mapper;

import org.springframework.stereotype.Component;
import itch.reservaciones.dto.AtenderDto;
import itch.reservaciones.entity.AtenderEntity;
import itch.reservaciones.entity.EmpleadoEntity;

@Component
public class AtenderMapper {

    public AtenderDto toDto(AtenderEntity entity) {
        AtenderDto dto = new AtenderDto();
        dto.setIdAtender(entity.getIdAtender());
        dto.setIdVenta(entity.getIdVenta());

        if (entity.getIdEmpleado() != null) {
            dto.setIdEmpleado(entity.getIdEmpleado().getIdEmpleado());
            dto.setNombreEmpleado(entity.getIdEmpleado().getNombreEmp());
        }

        return dto;
    }

    public AtenderEntity toEntity(AtenderDto dto) {
        AtenderEntity entity = new AtenderEntity();
        entity.setIdAtender(dto.getIdAtender());
        entity.setIdVenta(dto.getIdVenta());

        if (dto.getIdEmpleado() != null) {
            EmpleadoEntity empleado = new EmpleadoEntity();
            empleado.setIdEmpleado(dto.getIdEmpleado());
            entity.setIdEmpleado(empleado);
        }

        return entity;
    }
}
