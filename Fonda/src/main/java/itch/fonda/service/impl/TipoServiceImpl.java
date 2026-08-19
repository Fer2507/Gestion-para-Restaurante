package itch.fonda.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import itch.fonda.dto.TipoDto;
import itch.fonda.entity.TipoEntity;
import itch.fonda.mapper.TipoMapper;
import itch.fonda.repository.TipoRepository;
import itch.fonda.service.TipoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TipoServiceImpl implements TipoService{
	
	private TipoRepository tipoRepository;

	//Método para crear un nuevo tipo
    @Override
    public TipoDto createTipo(TipoDto tipoDto) {
    	TipoEntity tipo = TipoMapper.mapToTipo(tipoDto);
    	TipoEntity savedTipo = tipoRepository.save(tipo);
        return TipoMapper.mapToTipoDto(savedTipo);
    }

    //Método para obtener un tipo por su id
    @Override
    public TipoDto getTipoById(Integer id) {
    	TipoEntity tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + id));
        return TipoMapper.mapToTipoDto(tipo);
    }

    // Método para obtener todos los tipos
    @Override
    public List<TipoDto> getAllTipo() {
        List<TipoEntity> tipo =  tipoRepository.findAll();
        return tipo.stream().map(TipoMapper::mapToTipoDto).collect(Collectors.toList());
    }
    
    //Método para actualizar un tipo existente
    @Override
    public TipoDto updateTipo(Integer id, TipoDto updateTipoDto) {
        TipoEntity tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + id));

        tipo.setNombreTipo(updateTipoDto.getNombreTipo());
        tipo.setDescripcionTipo(updateTipoDto.getDescripcionTipo());


        TipoEntity updatedTipo = tipoRepository.save(tipo);
        return TipoMapper.mapToTipoDto(updatedTipo); // conviertes a DTO
    }
    
    //Método para eliminar un tipo por su id.
    @Override
    public void deleteTipo(Integer id) {
    	TipoEntity tipo = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + id));
    	tipoRepository.delete(tipo);
    }

}