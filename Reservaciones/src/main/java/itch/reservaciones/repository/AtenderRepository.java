package itch.reservaciones.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import itch.reservaciones.entity.AtenderEntity;

public interface AtenderRepository extends JpaRepository<AtenderEntity, Integer> {

    // Busca por empleado
	List<AtenderEntity> findByIdEmpleado_IdEmpleado(Integer idEmpleado);

    // Busca por venta
	List<AtenderEntity> findByIdVenta(Integer idVenta);
}
