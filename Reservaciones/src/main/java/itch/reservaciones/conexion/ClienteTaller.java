package itch.reservaciones.conexion;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import itch.reservaciones.dto.ClienteDto;

import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class ClienteTaller {

    private final RestTemplate restTemplate;

    public ClienteTaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClienteDto obtenerClientePorId(Integer idCliente) {
        String url = "http://localhost:7070/api/cliente/" + idCliente;
        try {
            String token = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getCredentials()
                    .toString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<ClienteDto> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, ClienteDto.class);

            return response.getBody();

        } catch (Exception e) {
            return null;
        }
    }
}
