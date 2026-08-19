package itch.fonda.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import itch.fonda.conexion.AtenderReservaciones;
import itch.fonda.dto.AtenderDto;
import itch.fonda.dto.EmpleadoDto;
import itch.fonda.dto.VentaDto;
import itch.fonda.segurity.EmpleadoClient;
import itch.fonda.service.TicketService;
import itch.fonda.service.VentaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }
    
    @Autowired  private TicketService ticketService;
	@Autowired 	private EmpleadoClient empleadoClient;
   	@Autowired 	private AtenderReservaciones atenderReservaciones;

    // Crear venta con detalles y empleado incluido en el body
    @PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<VentaDto> crearVenta(@RequestBody VentaDto ventaDto) {
        VentaDto ventaCreada = ventaService.crearVenta(ventaDto, ventaDto.getIdEmpleado());
        return ResponseEntity.ok(ventaCreada);
    }

    // Listar todas las ventas
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<VentaDto>> listarVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodasVentas());
    }

    // Obtener venta por ID
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{idVenta}")
    public ResponseEntity<VentaDto> obtenerVenta(@PathVariable Integer idVenta) {
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(idVenta));
    }

    // Obtener ventas por cliente
    @PreAuthorize("hasAuthority('LISTAR_CLIENTES') or hasRole('ADMINISTRADOR')")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<VentaDto>> obtenerVentasPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ventaService.obtenerVentasPorCliente(idCliente));
    }

    // Actualizar venta
    @PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
    @PutMapping("/{idVenta}")
    public ResponseEntity<VentaDto> actualizarVenta(@PathVariable Integer idVenta,
                                                    @RequestBody VentaDto ventaDto) {
        VentaDto ventaActualizada = ventaService.actualizarVenta(idVenta, ventaDto, ventaDto.getIdEmpleado());
        return ResponseEntity.ok(ventaActualizada);
    }

    // Eliminar venta
    @PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{idVenta}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer idVenta) {
        ventaService.eliminarVenta(idVenta);
        return ResponseEntity.ok("Venta eliminada con éxito (ID: " + idVenta + ")");
    }
    
    @PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<VentaDto>> obntenerPorIdReserva(@PathVariable Integer idReserva){
    	List<VentaDto> ventas = ventaService.obtenerPorIdReserva(idReserva);
    	return ResponseEntity.ok(ventas);
    }
    
    @PreAuthorize("hasAuthority('GESTIONAR_RESERVAS') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/reserva/{idReserva}")
    public ResponseEntity<String> eliminarPorIdReserva(@PathVariable Integer idReserva){
    	ventaService.eliminarPorIdReserva(idReserva);
    	return ResponseEntity.ok("Ventas con Id: " + idReserva +" eliminada con exito");
    }
    
    @PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/ticket/{idVenta}")
   	public ResponseEntity<byte[]> generarTicket(@PathVariable Integer idVenta) {
    	try {
    		VentaDto  venta = ventaService.obtenerVentaPorId(idVenta);
    		if (venta == null) {
    			return ResponseEntity.notFound().build();
    		}
    		byte[] pdfByte = ticketService.generarTicket(venta);
    		
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.APPLICATION_PDF);
    		headers.setContentDispositionFormData("Inline", "ticket_" + idVenta + ".pdf");
    		
    		return new ResponseEntity<>(pdfByte, headers, HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
   	
   	//BUSQUEDA
   	@PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
   	@GetMapping("/buscar")
   	public ResponseEntity<List<VentaDto>> buscarPorFecha(@RequestParam("fecha") String fechaStr) {
   	    LocalDate fecha;
   	    try {
   	        fecha = LocalDate.parse(fechaStr); // parsea yyyy-MM-dd
   	    } catch (Exception e) {
   	        return ResponseEntity.badRequest().body(null);
   	    }
        List<VentaDto> ventas = ventaService.buscarPorFecha(fecha);
        return ResponseEntity.ok(ventas);
    }

   	@PreAuthorize("hasAuthority('REALIZAR_VENTAS') or hasRole('ADMINISTRADOR')")
   	@GetMapping("/mesero/mis-ventas")
   	public ResponseEntity<?> misVentas(Authentication auth) {


   	    String username = auth.getName();

   	    EmpleadoDto empleado = empleadoClient.obtenerEmpleadoPorNombre(username);

   	    if (empleado == null) {
   	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
   	                .body("No existe empleado asociado al usuario: " + username);
   	    }

   	    List<AtenderDto> atenciones =
   	            atenderReservaciones.obtenerPorEmpleado(empleado.getIdEmpleado());

   	    List<VentaDto> ventas = atenciones.stream()
   	            .map(a -> ventaService.obtenerVentaPorId(a.getIdVenta()))
   	            .toList();

   	    return ResponseEntity.ok(ventas);
   	    }
}
