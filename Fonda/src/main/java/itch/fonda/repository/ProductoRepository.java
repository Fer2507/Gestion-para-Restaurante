package itch.fonda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itch.fonda.entity.ProductoEntity;

@Repository

public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
	List<ProductoEntity> findByActivoTrue();   // productos activos
	List<ProductoEntity> findByActivoFalse();  // productos inactivos
	
	 List<ProductoEntity> findByPrecioProductoBetween(Double min, Double max);
	 List<ProductoEntity> findByTipoNombreTipoContainingIgnoreCase(String nombreTipo);
	 List<ProductoEntity> findByNombreProductoContainingIgnoreCase(String nombreProducto);
}
