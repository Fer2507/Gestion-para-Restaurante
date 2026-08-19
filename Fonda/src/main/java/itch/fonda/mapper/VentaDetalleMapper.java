package itch.fonda.mapper;

import org.springframework.stereotype.Component;
import itch.fonda.dto.VentaDetalleDto;
import itch.fonda.entity.ProductoEntity;
import itch.fonda.entity.VentaDetalleEntity;
import itch.fonda.entity.VentaEntity;
import itch.fonda.repository.ProductoRepository;

@Component
public class VentaDetalleMapper {

    private final ProductoRepository productoRepository;

    public VentaDetalleMapper(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- Entity -> DTO ---
    public VentaDetalleDto toDto(VentaDetalleEntity entity) {
        if (entity == null) return null;

        VentaDetalleDto dto = new VentaDetalleDto();
        dto.setIdDetalle(entity.getIdDetalle());
        dto.setIdVenta(entity.getVenta() != null ? entity.getVenta().getIdVenta() : null);
        dto.setIdCliente(entity.getVenta() != null ? entity.getVenta().getIdCliente() : null);
        dto.setIdProducto(entity.getProducto().getIdProducto());
        dto.setCantidad(entity.getCantidad());
        dto.setSubtotal(entity.getSubtotal());

        // Llenar el objeto ProductoSimple
        if (entity.getProducto() != null) {
            dto.setProducto(
                new VentaDetalleDto.ProductoSimple(
                    entity.getProducto().getNombreProducto(),
                    entity.getProducto().getPrecioProducto()
                )
            );
        }

        return dto;
    }

    // --- DTO -> Entity ---
    public VentaDetalleEntity toEntity(VentaDetalleDto dto, VentaEntity venta) {
        if (dto == null) return null;

        VentaDetalleEntity entity = new VentaDetalleEntity();
        entity.setVenta(venta);
        entity.setCantidad(dto.getCantidad());

        // Buscar el producto real en la BD
        ProductoEntity producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException(
                    "Producto con id " + dto.getIdProducto() + " no encontrado"
                ));

        entity.setProducto(producto);
        entity.setPrecio(producto.getPrecioProducto());
        entity.calcularSubtotal();

        return entity;
    }
}
