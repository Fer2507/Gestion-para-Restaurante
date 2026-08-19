package itch.reservaciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Atender")
public class AtenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atender")
    private Integer idAtender;

    //Relación con empleado (dentro del mismo servicio)
    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntity idEmpleado;

    //Campo simple (viene del microservicio Fonda)
    @Column(name = "id_venta", nullable = false)
    private Integer idVenta;
}
