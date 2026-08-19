package itch.fonda.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import itch.fonda.conexion.ClienteTaller;
import itch.fonda.conexion.AtenderReservaciones;
import itch.fonda.dto.ClienteDto;
import itch.fonda.dto.VentaDetalleDto;
import itch.fonda.dto.VentaDto;
import itch.fonda.dto.AtenderDto;
import itch.fonda.entity.ProductoEntity;
import itch.fonda.entity.VentaDetalleEntity;
import itch.fonda.entity.VentaEntity;
import itch.fonda.repository.ProductoRepository;

@Component
public class VentaMapper {

    private final ClienteTaller clienteTaller;
    private final ProductoRepository productoRepository;
    private final AtenderReservaciones atenderReservaciones; // Para obtener info del empleado

    public VentaMapper(ClienteTaller clienteTaller, ProductoRepository productoRepository,
                       AtenderReservaciones atenderReservaciones) {
        this.clienteTaller = clienteTaller;
        this.productoRepository = productoRepository;
        this.atenderReservaciones = atenderReservaciones;
    }

    // --- Entity -> DTO ---
    public VentaDto toDto(VentaEntity entity) {
        if (entity == null) return null;

        VentaDto dto = new VentaDto();
        dto.setIdVenta(entity.getIdVenta());
        dto.setIdCliente(entity.getIdCliente());
        dto.setIdEmpleado(entity.getIdEmpleado());
        dto.setFechaCompra(entity.getFechaCompra());
        dto.setTotal(entity.getTotal());
        dto.setIdReserva(entity.getIdReserva());

        // Obtener nombre del cliente
        ClienteDto cliente = clienteTaller.obtenerClientePorId(entity.getIdCliente());
        dto.setNombreCliente(cliente != null ? cliente.getNombreCliente() : "Desconocido");
      
        // Obtener nombre del empleado a través del servicio AtenderReservaciones
        if (entity.getIdEmpleado() != null) {
            List<AtenderDto> atenciones = atenderReservaciones.obtenerPorEmpleado(entity.getIdEmpleado());
            
            if (atenciones != null && !atenciones.isEmpty()) {
                dto.setNombreEmpleado(atenciones.get(0).getNombreEmpleado()); // Usa el primero
            } else {
                dto.setNombreEmpleado("Desconocido");
            }
        }

        // Mapear detalles
        if (entity.getDetalles() != null && !entity.getDetalles().isEmpty()) {
            List<VentaDetalleDto> detallesDto = entity.getDetalles()
                .stream()
                .map(detalle -> {
                    VentaDetalleDto d = new VentaDetalleDto();
                    d.setIdVenta(entity.getIdVenta());
                    d.setIdCliente(entity.getIdCliente());
                    d.setIdDetalle(detalle.getIdDetalle());
                    d.setIdProducto(detalle.getProducto().getIdProducto());
                    d.setCantidad(detalle.getCantidad());
                    d.setSubtotal(detalle.getSubtotal());

                    d.setProducto(new VentaDetalleDto.ProductoSimple(
                        detalle.getProducto().getNombreProducto(),
                        detalle.getProducto().getPrecioProducto()
                    ));

                    return d;
                })
                .collect(Collectors.toList());

            dto.setDetalles(detallesDto);
        }

        return dto;
    }

    // --- DTO -> Entity ---
    public VentaEntity toEntity(VentaDto dto) {
        if (dto == null) return null;

        VentaEntity entity = new VentaEntity();
        entity.setIdVenta(dto.getIdVenta());
        entity.setIdCliente(dto.getIdCliente());
        entity.setIdEmpleado(dto.getIdEmpleado());
        entity.setFechaCompra(dto.getFechaCompra() != null ? dto.getFechaCompra() : java.time.LocalDateTime.now());
        entity.setIdReserva(dto.getIdReserva());
    
        // Inicializa o limpia lista de detalles
        if (entity.getDetalles() == null) {
            entity.setDetalles(new java.util.ArrayList<>());
        } else {
            entity.getDetalles().clear();
        }

        // Mapear detalles
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<VentaDetalleEntity> detalles = dto.getDetalles()
                .stream()
                .map(detalleDto -> {
                    ProductoEntity producto = productoRepository.findById(detalleDto.getIdProducto())
                        .orElseThrow(() -> new RuntimeException(
                            "Producto con id " + detalleDto.getIdProducto() + " no encontrado"
                        ));

                    VentaDetalleEntity detalle = new VentaDetalleEntity();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleDto.getCantidad());
                    detalle.setPrecio(producto.getPrecioProducto());
                    detalle.calcularSubtotal();
                    detalle.setVenta(entity); // mantener la relación

                    return detalle;
                })
                .collect(Collectors.toList());

            entity.setDetalles(detalles);
        }

        entity.calcularTotal();
        return entity;
    }
}
