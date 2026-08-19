package itch.reservaciones.config;

import lombok.RequiredArgsConstructor;
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

import itch.reservaciones.segurity.JwtAuthFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ====== RUTAS PÚBLICAS ======
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/health", "/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/atender/**").permitAll()
                
                // ====== RESERVAS ======
                .requestMatchers("/api/reservas/**")
                    .hasAnyAuthority("CREAR_RESERVA", "GESTIONAR_RESERVAS", "ROLE_ADMINISTRADOR")

                // ====== MESAS ======
                .requestMatchers(HttpMethod.GET, "/api/mesa/**")
                    .hasAnyAuthority("CREAR_RESERVA", "GESTIONAR_MESAS", "ROLE_ADMINISTRADOR")
                .requestMatchers("/api/mesa/**")
                    .hasAnyAuthority("GESTIONAR_MESAS", "ROLE_ADMINISTRADOR")

                // ====== EMPLEADOS ======
                .requestMatchers("/api/empleado/**")
                    .hasAnyAuthority("GESTIONAR_EMPLEADOS", "ROLE_ADMINISTRADOR", "ROLE_SUPERVISOR", "ROLE_MESERO")

                // ====== PEDIDOS / ATENDER ======
                .requestMatchers("/api/atender/**")
                    .hasAnyAuthority("GESTIONAR_PEDIDOS", "ROLE_ADMINISTRADOR")

                // ====== TODO LO DEMÁS: REQUIERE TOKEN ======
                .anyRequest().authenticated()
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

    // Solución para evitar usuarios generados automáticamente
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication) {
                return null; // el JWT se encarga
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}
