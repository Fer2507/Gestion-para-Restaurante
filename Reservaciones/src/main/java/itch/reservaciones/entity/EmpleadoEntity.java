package itch.reservaciones.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Empleado")
public class EmpleadoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_empleado")
	private Integer idEmpleado;
	
	@Column(name = "nombre_emp")
	private String nombreEmp;
	
	@Column(name = "puesto")
	private String puesto;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "usuario_id")
	private Integer idUsuario;

	@Column(name = "email")
	private String email;
	
}
