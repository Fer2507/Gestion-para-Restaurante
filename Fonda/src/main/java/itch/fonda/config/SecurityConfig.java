package itch.fonda.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import itch.fonda.segurity.JwtAuthFilter;


@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

             // rutas públicas
                 .requestMatchers("/health", "/actuator/**").permitAll()
                 .requestMatchers("/api/producto/img/**").permitAll()
                
                 // Rutas públicas de productos
                 .requestMatchers(HttpMethod.GET, "/api/producto/activos").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/producto/buscar").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/producto/buscarPorTipo").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/producto/buscarEntrePrecios").permitAll()
                 
                // === VENTAS ===
                 .requestMatchers("/api/ventas/mesero/mis-ventas")
                 .hasAnyAuthority("REALIZAR_VENTAS", "GESTIONAR_RESERVAS", "GESTION_PEDIDOS", "ROLE_ADMINISTRADOR")
                .requestMatchers("/api/ventas/**")
                    .hasAnyAuthority("REALIZAR_VENTAS", "GESTION_PEDIDOS", "GESTIONAR_RESERVAS","LISTAR_CLIENTES", "ROLE_ADMINISTRADOR","GESTIONAR_EMPLEADOS")
                    // === PRODUCTOS ===
                .requestMatchers("/api/productos/**")
                    .hasAnyAuthority("GESTIONAR_PRODUCTOS", "ROLE_ADMINISTRADOR", "VISTA_PRINCIPAL")
                  //=== TIPOS ===
                 .requestMatchers("/api/tipo/**")
                    .hasAnyAuthority("GESTIONAR_TIPOS", "ROLE_ADMINISTRADOR")
                
                // Todo lo demás requiere autenticación
                .anyRequest()
                    .authenticated()
            )

            .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .cors();

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // —— Solución para evitar usuarios generados automáticamente ——
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication) {
                return null; // El JWTFilter se encarga
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}
