package itch.reservaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import itch.reservaciones.entity.EmpleadoEntity;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {

	List<EmpleadoEntity> findByNombreEmpContainingIgnoreCase(String nombreEmp);
	List<EmpleadoEntity> findByPuestoContainingIgnoreCase(String puesto);
	Optional<EmpleadoEntity> findByNombreEmp(String nombreEmp);
}
