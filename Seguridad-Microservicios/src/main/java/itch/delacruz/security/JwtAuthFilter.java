package itch.delacruz.security;

import itch.delacruz.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**token JWT válido en el encabezado.*/
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    	// utas públicas que NO deben pasar por validación JWT
    	String path = request.getServletPath();

    	if (path.equals("/api/usuarios/crear") ||
    	    path.equals("/auth/login") ||
    	    path.equals("/auth/register")) {

    	    filterChain.doFilter(request, response);
    	    return;
    	}
    	
        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        //Validar encabezado Authorization y extraer el token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
            } catch (Exception e) {
                logger.warn("❌ Token inválido: " + e.getMessage());
            }
        }

        //Si hay token y el contexto aún no tiene autenticación
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = jwtService.extractAllClaims(token);

                //Rol principal
                String rol = (String) claims.get("rol");

                //Permisos
                List<String> permisos = (List<String>) claims.get("permisos");

                // Convertimos rol y permisos a authorities reconocibles por Spring Security
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                // Agregamos el rol como ROLE_*
                authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));

                // Agregamos cada permiso individual
                if (permisos != null) {
                    authorities.addAll(permisos.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
                }

                // Crear el objeto de autenticación con authorities
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, token, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guardamos la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                logger.error("Error procesando el token JWT: " + e.getMessage());
            }
        }

        // Continuar con el resto de filtros
        filterChain.doFilter(request, response);
    }
}

