package itch.fonda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "venta_detalle")
public class VentaDetalleEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle") 
    private Integer idDetalle;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta")
    private VentaEntity venta;   // relación con VentaEntity // Agrupa productos de la misma venta
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;  // relación con Producto
    
    @Column(name = "cantidad")
    private Integer cantidad = 1;
    
    @Column(name = "precio_unitario")
    private Double precio;

    @Column(name = "subtotal")
    private Double subtotal;
    
    public void calcularSubtotal() {
        if (precio != null && cantidad != null) {
            this.subtotal = precio * cantidad;
        } else {
            this.subtotal = 0.0;
        }
    }

}
