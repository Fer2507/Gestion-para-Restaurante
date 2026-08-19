package itch.fonda.conexion;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import itch.fonda.dto.AtenderDto;
import itch.fonda.util.JwtUtil;

@Component
public class AtenderReservaciones {

    private final RestTemplate restTemplate;

    public AtenderReservaciones(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<?> crearEntidad(Object body) {
        HttpHeaders headers = new HttpHeaders();
        String token = JwtUtil.getTokenActual();

        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return (body == null) ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);
    }

    // -----------------------------
    //  GET: Obtener atender por venta
    // -----------------------------
    public AtenderDto obtenerPorVenta(Integer idVenta) {
        String url = "http://localhost:7072/api/atender/venta/" + idVenta;

        try {
            ResponseEntity<AtenderDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, crearEntidad(null), AtenderDto.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error al obtener atender por venta: " + e.getMessage());
            return null;
        }
    }

    // -----------------------------
    //  POST: Crear atender
    // -----------------------------
    public AtenderDto crearAtender(AtenderDto atenderDto) {
        String url = "http://localhost:7072/api/atender";

        try {
            ResponseEntity<AtenderDto> response = restTemplate.exchange(
                    url, HttpMethod.POST, crearEntidad(atenderDto), AtenderDto.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error al crear atender: " + e.getMessage());
            return null;
        }
    }

    // Crear usando valores directos
    public AtenderDto crearAtender(Integer idEmpleado, Integer idVenta) {
        AtenderDto dto = new AtenderDto();
        dto.setIdEmpleado(idEmpleado);
        dto.setIdVenta(idVenta);
        return crearAtender(dto);
    }

    // -----------------------------
    //  GET: Obtener por empleado
    // -----------------------------
    public List<AtenderDto> obtenerPorEmpleado(Integer idEmpleado) {
        String url = "http://localhost:7072/api/atender/empleado/" + idEmpleado;

        try {
            ResponseEntity<List<AtenderDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    crearEntidad(null),
                    new ParameterizedTypeReference<List<AtenderDto>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error al obtener atender por empleado: " + e.getMessage());
            return null;
        }
    }
}
