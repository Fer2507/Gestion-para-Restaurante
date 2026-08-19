package itch.reservaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDto {
	
	private Integer idEmpleado;
	private String nombreEmp;
	private String puesto;
	private String clave;
	private Integer idUsuario;
	private String email;
}
