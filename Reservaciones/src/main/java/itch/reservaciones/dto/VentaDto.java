package itch.reservaciones.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VentaDto {

    private Integer idVenta;
    private Integer idCliente;
    // Nombre del cliente solo para mostrar
    private String nombreCliente;
    
    private LocalDateTime fechaCompra;
    private Double total;
    private Integer idReserva;
    //Empleado
    private Integer idEmpleado;
    private String nombreEmpleado;

    // Una venta tiene varios detalles
    private List<VentaDetalleDto> detalles;
}
