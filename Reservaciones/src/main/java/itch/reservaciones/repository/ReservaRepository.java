package itch.reservaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import itch.reservaciones.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Integer> {
	
    List<ReservaEntity> findByIdCliente(Integer idCliente);
    
    List<ReservaEntity> findByIdMesa_IdMesa(Integer idMesa);
    
    List<ReservaEntity> findByFechaReservaBetween(LocalDateTime inicio, LocalDateTime fin);

}
