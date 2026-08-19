package itch.reservaciones.service;

import java.util.List;
import itch.reservaciones.dto.EmpleadoDto;

public interface EmpleadoService {
	//Crear Nuevo Empleado
	EmpleadoDto createEmpleado (EmpleadoDto empleadoDto);
	//Busacr empleado por Id
	EmpleadoDto getEmpleadoById(Integer Id);
	//Obtener todos los empleados
	List<EmpleadoDto> getAllEmpleados();
	//Contruir REST API para Actualizar empleado
	EmpleadoDto updateEmpleado(Integer Id, EmpleadoDto updateEmpleado);
	//Construir DELETE REST API empleado
	void deleteEmpleado(Integer Id);
	
	List<EmpleadoDto> buscarporNombre(String nombreEmp);
	
	List<EmpleadoDto> buscarporPuesto(String puesto);
	
	EmpleadoDto buscarEmpleadoExacto(String nombreEmp);

}
