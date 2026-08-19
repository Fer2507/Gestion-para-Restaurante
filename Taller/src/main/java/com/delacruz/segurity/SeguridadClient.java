package com.delacruz.segurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.delacruz.dto.UsuarioDto;

@Component
public class SeguridadClient {

    private final RestTemplate restTemplate;

    // URL base del microservicio de seguridad
    @Value("${seguridad.api.url}")
    private String seguridadApiUrl; // http://localhost:7073/api/usuarios

    public SeguridadClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    // Obtener token desde Spring Security
    private String getToken() {
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (credentials != null) {
            return credentials.toString();
        }
        throw new RuntimeException("Token JWT no disponible en el contexto de seguridad");
    }

    // Crear cabeceras HTTP con Authorization y Content-Type
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    // Crear usuario
    public UsuarioDto crearUsuario(UsuarioDto usuario) {
        String url = seguridadApiUrl + "/crear";
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(usuario, createHeaders());
        return restTemplate.postForObject(url, entity, UsuarioDto.class);
    }

    // Actualizar usuario
    public void actualizarUsuario(Integer idUsuario, UsuarioDto usuarioNuevo) {
        String url = seguridadApiUrl + "/actualizar/" + idUsuario;
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(usuarioNuevo, createHeaders());
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }
}
