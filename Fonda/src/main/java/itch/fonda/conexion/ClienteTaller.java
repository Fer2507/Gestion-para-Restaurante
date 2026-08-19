package itch.fonda.conexion;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import itch.fonda.dto.ClienteDto;
import itch.fonda.util.JwtUtil;

@Component
public class ClienteTaller {

    private final RestTemplate restTemplate;

    public ClienteTaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClienteDto obtenerClientePorId(Integer idCliente) {
        String url = "http://localhost:7070/api/cliente/" + idCliente;

        try {
            String token = JwtUtil.getTokenActual();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<ClienteDto> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, ClienteDto.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("Error al consultar cliente: " + e.getMessage());
            return null;
        }
    }
}
