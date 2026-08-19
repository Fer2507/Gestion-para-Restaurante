package itch.fonda.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itch.fonda.conexion.AtenderReservaciones;
import itch.fonda.dto.AtenderDto;
import itch.fonda.dto.VentaDto;
import itch.fonda.entity.VentaDetalleEntity;
import itch.fonda.entity.VentaEntity;
import itch.fonda.mapper.VentaMapper;
import itch.fonda.repository.VentaRepository;
import itch.fonda.service.VentaService;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final AtenderReservaciones atenderReservaciones;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            VentaMapper ventaMapper,
                            AtenderReservaciones atenderReservaciones) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.atenderReservaciones = atenderReservaciones;
    }

    @Override
    public VentaDto crearVenta(VentaDto ventaDto, Integer idEmpleado) {
        if (idEmpleado == null) {
            throw new RuntimeException("El idEmpleado no puede ser null");
        }

        VentaEntity venta = ventaMapper.toEntity(ventaDto);
        
        if (venta.getDetalles() == null) {
            venta.setDetalles(List.of()); // evitar null
        }

        VentaEntity guardada = ventaRepository.save(venta);

        // Crear relación en Atender
        AtenderDto atenderDto = new AtenderDto();
        atenderDto.setIdEmpleado(idEmpleado);
        atenderDto.setIdVenta(guardada.getIdVenta());
        atenderReservaciones.crearAtender(atenderDto);

        VentaDto response = ventaMapper.toDto(guardada);
        response.setIdEmpleado(idEmpleado);
        return response;
    }

    @Override
    public VentaDto actualizarVenta(Integer idVenta, VentaDto ventaDto, Integer idEmpleado) {
        if (idEmpleado == null) {
            throw new RuntimeException("El idEmpleado no puede ser null");
        }

        // Buscar venta existente
        VentaEntity existente = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + idVenta));

        // Mapear nuevos datos
        VentaEntity nuevosDatos = ventaMapper.toEntity(ventaDto);

        // Actualizar campos básicos
        existente.setIdCliente(nuevosDatos.getIdCliente());
        existente.setIdEmpleado(idEmpleado);

        // Limpiar y reemplazar detalles
        existente.getDetalles().clear();
        List<VentaDetalleEntity> nuevosDetalles = nuevosDatos.getDetalles();
        if (nuevosDetalles != null && !nuevosDetalles.isEmpty()) {
            for (VentaDetalleEntity detalle : nuevosDetalles) {
                detalle.setVenta(existente); // 🔥 relación inversa importante
                existente.getDetalles().add(detalle);
            }
        }

        // Recalcular total
        existente.calcularTotal();

        // Guardar cambios
        VentaEntity guardada = ventaRepository.save(existente);

        // Sincronizar con Atender (opcional)
        try {
            AtenderDto atenderDto = new AtenderDto();
            atenderDto.setIdEmpleado(idEmpleado);
            atenderDto.setIdVenta(guardada.getIdVenta());
            atenderReservaciones.crearAtender(atenderDto);
        } catch (Exception e) {
            System.err.println("⚠️ [ADVERTENCIA] No se pudo sincronizar con 'reservaciones': " + e.getMessage());
        }

        // Responder al frontend
        VentaDto response = ventaMapper.toDto(guardada);
        response.setIdEmpleado(idEmpleado);
        return response;
    }

    @Override
    public VentaDto obtenerVentaPorId(Integer idVenta) {
        VentaEntity venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + idVenta));
        return ventaMapper.toDto(venta);
    }

    @Override
    public List<VentaDto> obtenerVentasPorCliente(Integer idCliente) {
        List<VentaEntity> ventas = ventaRepository.findByIdCliente(idCliente);
        return ventas.stream().map(ventaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<VentaDto> obtenerTodasVentas() {
        List<VentaEntity> ventas = ventaRepository.findAll();
        return ventas.stream().map(ventaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void eliminarVenta(Integer idVenta) {
        ventaRepository.deleteById(idVenta);
    }
    
    @Override
    public List<VentaDto> obtenerPorIdReserva(Integer idReserva) {
    	List<VentaEntity> ventas = ventaRepository.findByIdReserva(idReserva);
    	return ventas.stream().map(ventaMapper::toDto)
    			.collect(Collectors.toList());
    }
    @Override
    public void eliminarPorIdReserva(Integer idReserva) {
    	List<VentaEntity> ventas = ventaRepository.findByIdReserva(idReserva);
    	if(ventas.isEmpty()) {
    		throw new RuntimeException("No se encontro ninguna venta asociada a la reserva");
    	}
    	ventaRepository.deleteAll(ventas);
    }
    
    //BUSQUEDA
    @Override
    public List<VentaDto> buscarPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);
        return ventaRepository.findByFechaCompraBetween(inicio, fin)
        		.stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }
}
