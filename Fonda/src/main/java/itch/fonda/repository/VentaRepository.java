package itch.fonda.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itch.fonda.entity.VentaEntity;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

    // Buscar ventas por cliente
    List<VentaEntity> findByIdCliente(Integer idCliente);

    List<VentaEntity> findByIdReserva(Integer idReserva);
    
    List<VentaEntity> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
    
}