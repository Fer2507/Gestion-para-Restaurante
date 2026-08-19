package itch.reservaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import itch.reservaciones.entity.MesaEntity;

public interface MesaRepository extends JpaRepository<MesaEntity, Integer> {
	// Buscar mesa por número
    Optional<MesaEntity> getMesaByNumero(Integer numero);
}
