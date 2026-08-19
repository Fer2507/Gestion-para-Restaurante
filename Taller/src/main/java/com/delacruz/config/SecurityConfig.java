package com.delacruz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.delacruz.segurity.JwtAuthFilter;

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
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .authorizeHttpRequests(auth -> auth

                // Rutas públicas
                .requestMatchers("/health", "/actuator/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cliente/lista").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/cliente/**")
                .hasAnyAuthority("DAR_BAJA_CLIENTE", "ROLE_ADMINISTRADOR")
                // Clientes
                .requestMatchers("/api/cliente/buscar").hasAnyAuthority("LISTAR_CLIENTES","ROLE_ADMINISTRADOR","ROLE_MESERO")
                .requestMatchers("/api/cliente/**").hasAnyAuthority(
                    "LISTAR_CLIENTES",
                    "GESTIONAR_CLIENTES",
                    "DAR_BAJA_CLIENTE",
                    "CREAR_RESERVA",
                    "ROLE_ADMINISTRADOR",
                    "ROLE_MESERO",
                    "REALIZAR_VENTAS",
                    "ROLE_CAJERO"
                )

                // Todo lo demás requiere JWT
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

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) {
                return null;
            }
            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}
