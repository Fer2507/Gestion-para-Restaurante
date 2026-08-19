package itch.reservaciones.segurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String SECRET = "MiClaveSecretaParaLos4Microservicios1234567890";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            // EXTRAER PERMISOS Y ROL
            List<String> permisos = claims.get("permisos", List.class);
            String rol = claims.get("rol", String.class);

            System.out.println("JWT decodificado para usuario: " + username);
            System.out.println("Permisos en token: " + permisos);
            System.out.println("Rol en token: " + rol);

            List<SimpleGrantedAuthority> authorities = permisos != null
                    ? permisos.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    : Collections.emptyList();

            // Agregar el ROLE_ para que hasRole funcione
            if (rol != null && !rol.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
            }

            System.out.println("Authorities cargadas en Spring Security: " + authorities);

            // Crear UserDetails para @AuthenticationPrincipal
            UserDetails userDetails = User
                    .withUsername(username)
                    .password("") // No se usa en JWT, puede ir vacío
                    .authorities(authorities)
                    .build();

            // Solo si no está autenticado aún
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, token, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Usuario autenticado en Spring Security: " + username);
            }

        }  catch (Exception e) {
        	System.err.println("Error validando JWT: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
