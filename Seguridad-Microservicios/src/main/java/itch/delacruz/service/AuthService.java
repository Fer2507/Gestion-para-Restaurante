package itch.delacruz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import itch.delacruz.dto.AuthRequest;
import itch.delacruz.dto.AuthResponse;
import itch.delacruz.entity.Usuario;
import itch.delacruz.repository.UsuarioRepository;

@Service
public class AuthService {

	@Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepo;

    /*Autentica un usuario y devuelve un token JWT*/
    public AuthResponse login(AuthRequest request) {
        // Validar credenciales
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Buscar usuario
        Usuario usuario = usuarioRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar token
        String token = jwtService.generateToken(usuario);

        // Responder con token, rol y permisos
        return new AuthResponse(
                token,
                usuario.getUsername(),
                usuario.getRol().getNombre(),
                usuario.getRol().getPermisos().stream().map(p -> p.getNombre()).toList()
        );
    }
    /* Simulación de cierre de sesión (stateless) */
    public String logout(String username) {
        // En JWT no se borra el token del servidor.
        // Puedes registrar el cierre si deseas (por auditoría).
        return "El usuario " + username + " cerró sesión correctamente.";
    }
}