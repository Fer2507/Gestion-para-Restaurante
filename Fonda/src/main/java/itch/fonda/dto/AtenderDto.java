package itch.fonda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtenderDto {

    private Integer idAtender;
    private Integer idEmpleado;
    private Integer idVenta;

    // Campos adicionales opcionales (si deseas mostrar más info)
    private String nombreEmpleado;
    private String detalleVenta;
}
