package itch.fonda.segurity;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import itch.fonda.dto.EmpleadoDto;

@Component
public class EmpleadoClient {

    private final RestTemplate restTemplate;

    public EmpleadoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmpleadoDto obtenerEmpleadoPorNombre(String nombreUsuario) {

        String url = "http://localhost:7072/api/empleados/buscar?nombre=" + nombreUsuario;

        try {
            ResponseEntity<EmpleadoDto> response =
                    restTemplate.getForEntity(url, EmpleadoDto.class);

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error consultando empleado: " + e.getMessage());
            return null;
        }
    }
}
