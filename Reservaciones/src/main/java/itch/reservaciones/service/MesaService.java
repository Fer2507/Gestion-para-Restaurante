package itch.reservaciones.service;

import java.util.List;

import itch.reservaciones.dto.MesaDto;

public interface MesaService {
	List<MesaDto> getAllMesas();           // Obtener todas las mesas

    MesaDto getMesaById(Integer id);         // Buscar mesa por ID

    MesaDto getMesaByNumero(Integer numero); // Buscar mesa por número
    
    MesaDto guardarMesa(MesaDto mesaDto); //Guardar una mesa nueva
    
    MesaDto actualizarMesa(Integer id, MesaDto mesaDto); //Actualizar mesa
    
    void eliminarMesa(Integer id); //Eliminar mesa
}
