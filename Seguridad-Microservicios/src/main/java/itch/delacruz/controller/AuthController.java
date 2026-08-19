package itch.delacruz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import itch.delacruz.dto.AuthRequest;
import itch.delacruz.dto.AuthResponse;
import itch.delacruz.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
    
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado"; // Thymeleaf: acceso-denegado.html
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam(required = false) String username) {
        // opcional: podrías registrar el nombre del usuario que cerró sesión
        String mensaje = (username != null)
                ? "El usuario " + username + " cerró sesión correctamente"
                : "Sesión cerrada correctamente";
        return ResponseEntity.ok(mensaje);
    }
}
