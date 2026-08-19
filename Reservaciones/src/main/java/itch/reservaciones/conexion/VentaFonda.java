package itch.reservaciones.conexion;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import itch.reservaciones.dto.VentaDto;
import itch.reservaciones.util.JwtUtil;

@Component
public class VentaFonda {

    private final RestTemplate restTemplate;

    public VentaFonda(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Obtener una venta por ID
    public VentaDto obtenerVentaPorId(Integer idVenta) {
        String url = "http://localhost:7071/api/ventas/" + idVenta;

        try {
        	 String token = JwtUtil.getTokenActual();

             HttpHeaders headers = new HttpHeaders();
             headers.set("Authorization", "Bearer " + token);
             headers.setContentType(MediaType.APPLICATION_JSON);
        	
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<VentaDto> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, VentaDto.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("Venta no encontrada: " + e.getMessage());
            return null;
        }
    }

    // Crear venta
    public VentaDto crearVenta(VentaDto ventaDto) {
        String url = "http://localhost:7071/api/ventas";

        try { String token = JwtUtil.getTokenActual();

		        HttpHeaders headers = new HttpHeaders();
		        headers.set("Authorization", "Bearer " + token);
		        headers.setContentType(MediaType.APPLICATION_JSON);
		   	
		       HttpEntity<Void> entity = new HttpEntity<>(headers);
            
		       ResponseEntity<VentaDto> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, VentaDto.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("Error al crear venta: " + e.getMessage());
            return null;
        }
    }

    // Eliminar venta
    public void eliminarVenta(Integer idVenta) {
        String url = "http://localhost:7071/api/ventas/" + idVenta;

        try {
        	 String token = JwtUtil.getTokenActual();

             HttpHeaders headers = new HttpHeaders();
             headers.set("Authorization", "Bearer " + token);
             headers.setContentType(MediaType.APPLICATION_JSON);
        	
            HttpEntity<Void> entity = new HttpEntity<>(headers);
        	
            restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        } catch (Exception e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
        }
    }

    // Obtener ventas por reserva
    public List<VentaDto> obtenerVentasPorReserva(Integer idReserva) {
        String url = "http://localhost:7071/api/ventas/reserva/" + idReserva;

        try {
        	
        	 String token = JwtUtil.getTokenActual();

             HttpHeaders headers = new HttpHeaders();
             headers.set("Authorization", "Bearer " + token);
             headers.setContentType(MediaType.APPLICATION_JSON);
        	
            HttpEntity<Void> entity = new HttpEntity<>(headers);
        	
            ResponseEntity<VentaDto[]> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, VentaDto[].class);

            return Arrays.asList(response.getBody());

        } catch (Exception e) {
            System.out.println("No se encontraron ventas: " + e.getMessage());
            return List.of();
        }
    }
}
