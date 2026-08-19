package itch.reservaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import itch.reservaciones.dto.EmpleadoDto;
import itch.reservaciones.service.EmpleadoService;

@RestController
@RequestMapping("api/empleado")
@CrossOrigin(origins = "http://localhost:5173")
public class EmpleadoController {
	@Autowired private EmpleadoService empleadoService;

	
	//Post: Empleado nuevo
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<EmpleadoDto> crearEmpleado(@RequestBody EmpleadoDto empleadoDto){
		EmpleadoDto guardarEmpleado = empleadoService.createEmpleado(empleadoDto);
		return new ResponseEntity<>(guardarEmpleado, HttpStatus.CREATED);
	}
	
	//GET:Obtener Empleado por Id
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')  or hasRole('MESERO')")
	@GetMapping("/{id}")
	public ResponseEntity<EmpleadoDto> getEmpleadoById(@PathVariable("id") Integer id){
		EmpleadoDto empleadoDto = empleadoService.getEmpleadoById(id);
		return ResponseEntity.ok(empleadoDto);
	}
	
	//GET: Obtener todos los empleado
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')  or hasRole('MESERO')")
	@GetMapping
	public ResponseEntity<List<EmpleadoDto>> getAllEmpleados(){
		List<EmpleadoDto> empleados = empleadoService.getAllEmpleados();
		return ResponseEntity.ok(empleados);
	}
	
	//PUT: Actualizar Empleado
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')")
	@PutMapping("/{id}")
	public ResponseEntity<EmpleadoDto> updateEmpleado(@PathVariable("id") Integer IdEmpleado,
													   @RequestBody EmpleadoDto updateEmpleadoDto){
		EmpleadoDto empleadoDto = empleadoService.updateEmpleado(IdEmpleado, updateEmpleadoDto);
		return ResponseEntity.ok(empleadoDto);
	}
	
	//DELETE: Eliminar un empleado
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmpleado(@PathVariable("id") Integer id){
		empleadoService.deleteEmpleado(id);
		return ResponseEntity.ok("Empleado dado de baja");
	}
	
	//BUSQUEDAS
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')  or hasAuthority('LISTAR_EMPLEADOS')")
	@GetMapping("/buscar")
	public ResponseEntity<List<EmpleadoDto>> buscarPornombre(@RequestParam("nombreEmp") String nombreEmp){
		 if(nombreEmp == null || nombreEmp.trim().isEmpty()) {
	            return ResponseEntity.ok(empleadoService.getAllEmpleados());
	        }
		List<EmpleadoDto> empleados = empleadoService.buscarporNombre(nombreEmp);
		return ResponseEntity.ok(empleados);
	}
	
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')")
	@GetMapping("/puesto")
	public ResponseEntity<List<EmpleadoDto>> buscarPorpuesto(@RequestParam("puesto") String puesto){
		if(puesto == null || puesto.trim().isEmpty()) {
            return ResponseEntity.ok(empleadoService.getAllEmpleados());
        }
		List<EmpleadoDto> empleados = empleadoService.buscarporPuesto(puesto);
		return ResponseEntity.ok(empleados);
	}
	
	@PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMINISTRADOR')  or hasAuthority('LISTAR_EMPLEADOS')")
	@GetMapping("/por-usuario")
	public ResponseEntity<EmpleadoDto> obtenerEmpleadoPorUsuario(@AuthenticationPrincipal User user) {
	    EmpleadoDto empleado = empleadoService.buscarEmpleadoExacto(user.getUsername());
	    return ResponseEntity.ok(empleado); // si no existe, regresa null
	}

}
