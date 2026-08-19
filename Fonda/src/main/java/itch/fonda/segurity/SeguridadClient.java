package itch.fonda.segurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SeguridadClient {

    private final RestTemplate restTemplate;

    // URL base del microservicio de seguridad
    @Value("${seguridad.api.url}")
    private String seguridadApiUrl; // http://localhost:7073/api/usuarios

    public SeguridadClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
   
}
