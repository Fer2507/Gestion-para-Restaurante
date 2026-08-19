package itch.reservaciones.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import itch.reservaciones.dto.ReservaDto;
import itch.reservaciones.service.ReservaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

	@Autowired private ReservaService reservaService;

    //Crear nueva reserva (estatus por defecto "Pendiente")
    @PreAuthorize("hasAuthority('CREAR_RESERVA') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ReservaDto> crearReserva(@RequestBody ReservaDto dto) {
        if (dto.getFechaReserva() == null) {
            dto.setFechaReserva(LocalDateTime.now());
        }
        if (dto.getEstatus() == null || dto.getEstatus().isEmpty()) {
            dto.setEstatus("Pendiente");
        }
        ReservaDto guardada = reservaService.registrarReserva(dto);
        return ResponseEntity.ok(guardada);
    }

    //Obtener todas las reservas
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<ReservaDto>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }
    //Obtener reservas por estatus (Pendiente, Confirmada, etc.)
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<ReservaDto>> obtenerPorEstatus(@PathVariable String estatus) {
        return ResponseEntity.ok(reservaService.obtenerPorEstatus(estatus));
    }
    
    //Obtener reserva por ID
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    //Obtener reservas por cliente
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReservaDto>> obtenerPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(reservaService.obtenerPorCliente(idCliente));
    }

    //Actualizar reserva (sin cambiar estatus)
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> actualizarReserva(@PathVariable Integer id, @RequestBody ReservaDto dto) {
        ReservaDto actualizado = reservaService.actualizarReserva(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    //Confirmar reserva -> cambia estatus a "Confirmada"
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<ReservaDto> confirmarReserva(@PathVariable Integer id) {
        ReservaDto confirmada = reservaService.cambiarEstatus(id, "Confirmada");
        return ResponseEntity.ok(confirmada);
    }

    //Poner en pendiente (por si se necesita volver atrás)
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @PutMapping("/pendiente/{id}")
    public ResponseEntity<ReservaDto> marcarPendiente(@PathVariable Integer id) {
        ReservaDto pendiente = reservaService.cambiarEstatus(id, "Pendiente");
        return ResponseEntity.ok(pendiente);
    }

    //Cancelar reserva → elimina la reserva y los pedidos asociados
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Integer id) {
        reservaService.cancelarReservaConPedidos(id);
        return ResponseEntity.ok("Reserva cancelada y pedidos eliminados");
    }

    //Eliminar completamente (sin verificar pedidos)
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Integer id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
    
  //BUSQUEDA
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/fecha")
    public ResponseEntity<List<ReservaDto>> buscarPorFecha(@RequestParam("fecha") String fechas){
    	LocalDate fecha;
    	try {
    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	     fecha = LocalDate.parse(fechas.trim(), formatter);

    	     List<ReservaDto> reservaciones = reservaService.buscarPorFecha(fecha);
    	     return ResponseEntity.ok(reservaciones);
    	}catch(Exception e) {
    		 System.out.println("❌ Error al parsear fecha: " + fechas + " -> " + e.getMessage());
    	     return ResponseEntity.badRequest().body(null);
    	}
    }
    
}
