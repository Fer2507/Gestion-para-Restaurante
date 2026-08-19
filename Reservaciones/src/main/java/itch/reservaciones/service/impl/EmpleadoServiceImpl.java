package itch.reservaciones.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import itch.reservaciones.dto.EmpleadoDto;
import itch.reservaciones.dto.UsuarioDto;
import itch.reservaciones.entity.EmpleadoEntity;
import itch.reservaciones.mapper.EmpleadoMapper;
import itch.reservaciones.repository.EmpleadoRepository;
import itch.reservaciones.segurity.SeguridadClient;
import itch.reservaciones.service.EmpleadoService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService{

    private final SeguridadClient seguridadClient;
	private EmpleadoRepository empleadoRepository;

	@Override
	public EmpleadoDto createEmpleado(EmpleadoDto empleadoDto) {
		EmpleadoEntity empleado = EmpleadoMapper.mapToEmpleadoEntity(empleadoDto);
		EmpleadoEntity savedEmpleado = empleadoRepository.save(empleado);
		
		// Crear usuario en el microservicio de seguridad
		 UsuarioDto usuario = new UsuarioDto();
	        usuario.setUsername(savedEmpleado.getNombreEmp());
	        usuario.setPassword(savedEmpleado.getClave());
	        usuario.setRol(savedEmpleado.getPuesto());
	        usuario.setEmail(savedEmpleado.getEmail());
	        
	        // Llamar al microservicio
	        UsuarioDto usuarioCreado = seguridadClient.crearUsuario(usuario);

	        //Guardar el ID del usuario dentro del empleado
	        savedEmpleado.setIdUsuario(usuarioCreado.getIdUsuario());
	        empleadoRepository.save(savedEmpleado);
        	System.out.println("Usuario Creado Correctamente");
		
		return EmpleadoMapper.mapToEmpleadoDto(savedEmpleado);
	}
	
	@Override
	public EmpleadoDto getEmpleadoById(Integer id) {
		EmpleadoEntity empleado = empleadoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Empleado con id: "+ id +" no emcontrado"));
		return EmpleadoMapper.mapToEmpleadoDto(empleado);
	}
	
	@Override
	public List<EmpleadoDto> getAllEmpleados(){
		List<EmpleadoEntity> empleado = empleadoRepository.findAll();
		return empleado.stream().map(EmpleadoMapper::mapToEmpleadoDto).collect(Collectors.toList());
	}
	
	@Override
	public EmpleadoDto updateEmpleado(Integer id, EmpleadoDto updateEmpleadoDto) {
		EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado con id: "+ id +" no emcontrado"));
	
        // Actualizar datos
        empleado.setNombreEmp(updateEmpleadoDto.getNombreEmp());
        empleado.setPuesto(updateEmpleadoDto.getPuesto());
        empleado.setClave(updateEmpleadoDto.getClave());
        empleado.setEmail(updateEmpleadoDto.getEmail());

        EmpleadoEntity updatedEmpleado = empleadoRepository.save(empleado);

        // Crear objeto para enviar al microservicio de seguridad
        UsuarioDto usuario = new UsuarioDto();
        usuario.setUsername(updatedEmpleado.getNombreEmp());
        usuario.setPassword(updatedEmpleado.getClave());
        usuario.setRol(updatedEmpleado.getPuesto());
        
     //si NO tiene usuario todavía, lo creamos
        if (updatedEmpleado.getIdUsuario() == null) {
            UsuarioDto usuarioCreado = seguridadClient.crearUsuario(usuario);
            updatedEmpleado.setIdUsuario(usuarioCreado.getIdUsuario());
            empleadoRepository.save(updatedEmpleado);
            System.out.println("Usuario creado (antes no existía)");
        } else {
            // Si YA tiene usuario → actualizar
            seguridadClient.actualizarUsuario(updatedEmpleado.getIdUsuario(), usuario);
            System.out.println("Usuario actualizado correctamente");
        }
        

        return EmpleadoMapper.mapToEmpleadoDto(updatedEmpleado);
	}
	
	@Override
	public void deleteEmpleado(Integer id) {
		EmpleadoEntity empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado con id: "+ id +" no emcontrado"));
		empleadoRepository.delete(empleado);
	}
	
	//BUSQUEDAS
	@Override
	public List<EmpleadoDto> buscarporNombre(String nombreEmp){
		List<EmpleadoEntity> empleados = empleadoRepository.findByNombreEmpContainingIgnoreCase(nombreEmp);
		return empleados.stream()
				.map(EmpleadoMapper::mapToEmpleadoDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<EmpleadoDto> buscarporPuesto(String puesto){
		List<EmpleadoEntity> empleados = empleadoRepository.findByPuestoContainingIgnoreCase(puesto);
		return empleados.stream()
				.map(EmpleadoMapper::mapToEmpleadoDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public EmpleadoDto buscarEmpleadoExacto(String nombreEmp) {

	    EmpleadoEntity empleado = empleadoRepository.findByNombreEmp(nombreEmp)
	            .orElse(null); // si no lo encuentra → NO es empleado
	    if (empleado == null) {
	        return null;
	    }
	    return EmpleadoMapper.mapToEmpleadoDto(empleado);
	}

}
