package itch.fonda.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VentaDetalleDto {

    private Integer idDetalle;
    private Integer idVenta;
 // NUEVO: id del cliente para validación
    private Integer idCliente;
    private Integer idProducto;
    private Integer cantidad;
 // Información del producto (resumida)
    private ProductoSimple producto;
    private Double subtotal;
 
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductoSimple {
        private String nombre;
        private Double precio;

        public ProductoSimple(String nombre, Double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }
    }
}
