package itch.reservaciones.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Reservar")
public class ReservaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reserva")
	private Integer idReserva;
	
	@ManyToOne
	@JoinColumn(name = "id_mesa")//CONECTA CON MESA
	@JsonIgnore
	private MesaEntity idMesa;
	
	@Column(name = "id_cliente")
	private Integer idCliente;
	
	@Column(name = "fecha_reserva")
	private LocalDateTime fechaReserva;
	
	@Column(name = "estatus")
    private String estatus = "Pendiente";
	
}
