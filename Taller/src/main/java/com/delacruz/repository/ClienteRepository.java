package com.delacruz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delacruz.entity.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
		List<Cliente> findByNombreClienteContainingIgnoreCase(String nombreCliente);
}
