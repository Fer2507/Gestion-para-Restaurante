package itch.reservaciones.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {
	
	private Integer idReserva;
	private Integer idMesa;
	private Integer idCliente;
	private LocalDateTime fechaReserva;
	private List<VentaDto> ventas = new ArrayList<>();
	private String estatus = "Pendiente";

	
	// Campos adicionales
    private String nombreCliente; 
    private String descripcionMesa;
    
 // Constructor solo con los 4 campos principales
    public ReservaDto(Integer idReserva, Integer idMesa, Integer idCliente, LocalDateTime fechaReserva, List<VentaDto> ventas) {
        this.idReserva = idReserva;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.fechaReserva = fechaReserva;        
        this.ventas = ventas != null ? ventas : new ArrayList<>();;
    }
}
