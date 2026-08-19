package itch.delacruz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.delacruz.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}