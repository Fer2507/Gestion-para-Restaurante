package itch.delacruz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.delacruz.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
