package itch.reservaciones.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import itch.reservaciones.dto.MesaDto;
import itch.reservaciones.entity.MesaEntity;
import itch.reservaciones.mapper.MesaMapper;
import itch.reservaciones.repository.MesaRepository;
import itch.reservaciones.service.MesaService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MesaServiceImpl implements MesaService{
    private MesaRepository mesaRepository;

    @Override
    public List<MesaDto> getAllMesas() {
    	List<MesaEntity> mesa = mesaRepository.findAll();
		return mesa.stream().map(MesaMapper::mapToMesaDto).collect(Collectors.toList());
    } 

    @Override
    public MesaDto getMesaById(Integer id) {
    	MesaEntity mesa = mesaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Mesa con id: "+ id +" no encontrada"));
		return MesaMapper.mapToMesaDto(mesa);
    }

    @Override
    public MesaDto getMesaByNumero(Integer numero) {
    	MesaEntity mesa = mesaRepository.getMesaByNumero(numero)
                .orElseThrow(() -> new RuntimeException("Mesa con número: " + numero + " no encontrada"));
        return MesaMapper.mapToMesaDto(mesa);
    }
    
    @Override
    public MesaDto guardarMesa(MesaDto mesaDto) {
        MesaEntity mesa = MesaMapper.mapToMesaEntity(mesaDto);
        MesaEntity guardada = mesaRepository.save(mesa);
        return MesaMapper.mapToMesaDto(guardada);
    }

    @Override
    public MesaDto actualizarMesa(Integer id, MesaDto mesaDto) {
    	MesaEntity existente = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada: " + id));

        existente.setNumero(mesaDto.getNumero());
        existente.setCapacidad(mesaDto.getCapacidad());
        existente.setEstado(mesaDto.getEstado());

        MesaEntity actualizada = mesaRepository.save(existente);
        return MesaMapper.mapToMesaDto(actualizada);
    }

    @Override
    public void eliminarMesa(Integer id) {
        mesaRepository.deleteById(id);
    }
}
