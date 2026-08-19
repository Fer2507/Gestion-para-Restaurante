package itch.reservaciones.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.reservaciones.conexion.ClienteTaller;
import itch.reservaciones.conexion.VentaFonda;
import itch.reservaciones.dto.ClienteDto;
import itch.reservaciones.dto.ReservaDto;
import itch.reservaciones.dto.VentaDto;
import itch.reservaciones.entity.MesaEntity;
import itch.reservaciones.entity.ReservaEntity;
import itch.reservaciones.mapper.ReservaMapper;
import itch.reservaciones.repository.MesaRepository;
import itch.reservaciones.repository.ReservaRepository;
import itch.reservaciones.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired  private ReservaRepository reservaRepository;

    @Autowired private MesaRepository mesaRepository;

    @Autowired  private ClienteTaller clienteTaller; // Microservicio clientes
    
    @Autowired private VentaFonda ventaFonda;

    @Override
    public ReservaDto registrarReserva(ReservaDto reservaDto) {

        // Asignar fecha automáticamente si no viene
        if (reservaDto.getFechaReserva() == null) {
            reservaDto.setFechaReserva(LocalDateTime.now());
        }
        if (reservaDto.getIdCliente() != null) {
            ClienteDto cliente = clienteTaller.obtenerClientePorId(reservaDto.getIdCliente());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente con id " + reservaDto.getIdCliente() + " no existe");
            }
        }

        // Validar mesa
        if (reservaDto.getIdMesa() == null) {
            throw new IllegalArgumentException("Debe especificar una mesa");
        }

        MesaEntity mesa = mesaRepository.findById(reservaDto.getIdMesa())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        //Asignar Estatus por defecto
        if (reservaDto.getEstatus() == null || reservaDto.getEstatus().isEmpty()) {
        	reservaDto.setEstatus("Pendiente");
        }
        
        // Mapear DTO a entidad y guardar
        ReservaEntity reserva = ReservaMapper.mapToReservaEntity(reservaDto, mesa);
        reserva = reservaRepository.save(reserva);

        // Obtener nombre del cliente desde microservicio
        String nombreCliente = null;
        if (reserva.getIdCliente() != null) {
            ClienteDto cliente = clienteTaller.obtenerClientePorId(reserva.getIdCliente());
            nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
        }
        // Obtener ventas asociadas
        List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(reserva.getIdReserva());
        if (ventas == null) ventas = new ArrayList<>();

        // Mapear entidad a DTO con campos adicionales
        return ReservaMapper.mapToReservaDto(reserva, nombreCliente, ventas);
    }

    @Override
    public List<ReservaDto> obtenerTodas() {
        return reservaRepository.findAll().stream()
                .map(r -> {
                    String nombreCliente = null;
                    if (r.getIdCliente() != null) {
                        ClienteDto cliente = clienteTaller.obtenerClientePorId(r.getIdCliente());
                        nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
                    }
                    
                    List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(r.getIdReserva());
                    if (ventas == null) ventas = new ArrayList<>();
                    
                    return ReservaMapper.mapToReservaDto(r, nombreCliente, ventas);
                                       
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDto obtenerPorId(Integer id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
       
        String nombreCliente = null;
        if (reserva.getIdCliente() != null) {
            ClienteDto cliente = clienteTaller.obtenerClientePorId(reserva.getIdCliente());
            nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
        }
        
        List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(reserva.getIdReserva());
        if (ventas == null) ventas = new ArrayList<>();
        
        return ReservaMapper.mapToReservaDto(reserva, nombreCliente, ventas);
    }

    @Override
    public ReservaDto actualizarReserva(Integer id, ReservaDto dto) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        // Validaciones
        if (dto.getIdMesa() != null) {
            MesaEntity mesa = mesaRepository.findById(dto.getIdMesa())
                    .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
            reserva.setIdMesa(mesa);
        }

        if (dto.getIdCliente() != null) {
            reserva.setIdCliente(dto.getIdCliente());
        }

        if (dto.getFechaReserva() != null) {
            reserva.setFechaReserva(dto.getFechaReserva());
        }

        reserva = reservaRepository.save(reserva);

        // Obtener nombre del cliente
        String nombreCliente = null;
        if (reserva.getIdCliente() != null) {
            ClienteDto cliente = clienteTaller.obtenerClientePorId(reserva.getIdCliente());
            nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
        }
        List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(reserva.getIdReserva());
        if (ventas == null) ventas = new ArrayList<>();

        return ReservaMapper.mapToReservaDto(reserva, nombreCliente, ventas);
    }
    
    @Override
    public List<ReservaDto> obtenerPorCliente(Integer idCliente) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getIdCliente() != null && r.getIdCliente().equals(idCliente))
                .map(r -> {
                    ClienteDto cliente = clienteTaller.obtenerClientePorId(idCliente);
                    String nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
                   
                    List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(r.getIdReserva());
                    if (ventas == null) ventas = new ArrayList<>();
                   
                    return ReservaMapper.mapToReservaDto(r, nombreCliente, ventas);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarReserva(Integer id) {
        reservaRepository.deleteById(id);
    }
    
    @Override
    public List<ReservaDto> obtenerPorEstatus(String estatus){
    	return reservaRepository.findAll().stream().filter(r -> estatus.equalsIgnoreCase(r.getEstatus())).map(r -> {
    		ClienteDto cliente = clienteTaller.obtenerClientePorId(r.getIdCliente());
    		String nombreCliente = cliente != null? cliente.getNombreCliente() : "Cliente desconocido";
    		
    		List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(r.getIdReserva());
            if (ventas == null) ventas = new ArrayList<>();
    		
    		return ReservaMapper.mapToReservaDto(r, nombreCliente, ventas);
    	})
    			.collect(Collectors.toList());
    }
    
    @Override
    public ReservaDto cambiarEstatus(Integer idReserva, String nuevoEstatus) {
    	ReservaEntity reserva = reservaRepository.findById(idReserva)
    			.orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    	
    	//VALIDAR FECHA DE RESERVA
    	LocalDate fechaReserva = reserva.getFechaReserva().toLocalDate();
        LocalDate hoy = LocalDate.now();

        if (nuevoEstatus.equalsIgnoreCase("Confirmada")
                && fechaReserva.isBefore(hoy)) {

            throw new IllegalStateException(
                "No puedes confirmar una reservación anterior a la fecha actual."
            );
        }
        
    	reserva.setEstatus(nuevoEstatus);
    	reserva = reservaRepository.save(reserva);
    	
    	ClienteDto cliente = clienteTaller.obtenerClientePorId(reserva.getIdCliente());
		String nombreCliente = cliente != null? cliente.getNombreCliente() : "Cliente desconocido";
		
		List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(reserva.getIdReserva());
        if (ventas == null) ventas = new ArrayList<>();
		
		return ReservaMapper.mapToReservaDto(reserva, nombreCliente, ventas); 
    }
    
    @Override
    public void cancelarReservaConPedidos(Integer idReserva) {
        ReservaEntity reserva = reservaRepository.findById(idReserva)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(idReserva);
        if (ventas != null) {
        	for (VentaDto venta : ventas) {
                ventaFonda.eliminarVenta(venta.getIdVenta());
            }
        }

        reservaRepository.delete(reserva);
    }
    
    @Override
    public List<ReservaDto> buscarPorFecha(LocalDate fecha){
    	LocalDateTime inicio = fecha.atStartOfDay();
    	LocalDateTime fin = fecha.atTime(LocalTime.MAX);
    	return reservaRepository.findByFechaReservaBetween(inicio, fin)
    			.stream().map(reserva -> {
    	            // Obtener nombre del cliente
    	            String nombreCliente = null;
    	            if (reserva.getIdCliente() != null) {
    	                ClienteDto cliente = clienteTaller.obtenerClientePorId(reserva.getIdCliente());
    	                nombreCliente = cliente != null ? cliente.getNombreCliente() : "Cliente desconocido";
    	            }

    	            // Obtener ventas asociadas
    	            List<VentaDto> ventas = ventaFonda.obtenerVentasPorReserva(reserva.getIdReserva());
    	            if (ventas == null) ventas = new ArrayList<>();

    	            return ReservaMapper.mapToReservaDto(reserva, nombreCliente, ventas);
    	        })
    			.collect(Collectors.toList());
    }
}
