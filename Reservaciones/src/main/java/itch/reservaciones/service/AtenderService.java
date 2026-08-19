package itch.reservaciones.service;

import java.util.List;
import itch.reservaciones.dto.AtenderDto;

public interface AtenderService {
	
	List<AtenderDto> listarTodos();

    List<AtenderDto> obtenerPorEmpleado(Integer idEmpleado);

    List<AtenderDto> obtenerPorVenta(Integer idVenta);

    AtenderDto crearAtender(AtenderDto atenderDto);
}
