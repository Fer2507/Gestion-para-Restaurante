package itch.delacruz.config;

import itch.delacruz.repository.UsuarioRepository;
import itch.delacruz.entity.Rol;
import itch.delacruz.entity.Usuario;
import itch.delacruz.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AdminInitializer {
/*
    @Bean
    CommandLineRunner initAdminUser(UsuarioRepository usuarioRepository,
                                   RolRepository rolRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificamos si el rol ADMINISTRADOR existe, si no, lo creamos
            Rol rolAdmin = rolRepository.findByNombre("ADMINISTRADOR")
                    .orElseGet(() -> {
                        Rol nuevoRol = new Rol();
                        nuevoRol.setNombre("ADMINISTRADOR");
                        return rolRepository.save(nuevoRol);
                    });

            // Verificamos si el usuario administrador ya existe
            Optional<Usuario> adminExistente = usuarioRepository.findByUsername("admin");

            if (adminExistente.isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("Admin");
                admin.setPassword(passwordEncoder.encode("Admin123"));
                admin.setEstado(true);
                admin.setRol(rolAdmin);
                usuarioRepository.save(admin);

                System.out.println("✅ Usuario administrador creado: admin / admin123");
            } else {
                System.out.println("ℹ️ El usuario administrador ya existe.");
            }
        };
    }*/
}
