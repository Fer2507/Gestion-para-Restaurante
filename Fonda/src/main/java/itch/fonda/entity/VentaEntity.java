package itch.fonda.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venta")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_cliente")
    private Integer idCliente;   // referencia lógica al cliente en restaurante

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra = LocalDateTime.now();

    @Column(name = "total")
    private Double total=0.0;
    
    //Empleado
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    
    @Column(name = "id_Reserva")
    private Integer idReserva;

    // Relación: una venta tiene varios detalles
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VentaDetalleEntity> detalles = new java.util.ArrayList<>();
    
    public void calcularTotal() {
        if (detalles != null && !detalles.isEmpty()) {
            this.total = detalles.stream()
            		             .filter(d -> d != null && d.getSubtotal() != null)
                                 .mapToDouble(VentaDetalleEntity::getSubtotal)
                                 .sum();
        } else {
            this.total = 0.0;
        }
    }
}
