package itch.reservaciones.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.reservaciones.dto.AtenderDto;
import itch.reservaciones.entity.AtenderEntity;
import itch.reservaciones.mapper.AtenderMapper;
import itch.reservaciones.repository.AtenderRepository;
import itch.reservaciones.service.AtenderService;

@Service
public class AtenderServiceImpl implements AtenderService {

    @Autowired
    private AtenderRepository atenderRepository;

    @Autowired
    private AtenderMapper atenderMapper;
    
    @Override
    public List<AtenderDto> listarTodos() {
        return atenderRepository.findAll()
                .stream()
                .map(atenderMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<AtenderDto> obtenerPorEmpleado(Integer idEmpleado) {
        return atenderRepository.findByIdEmpleado_IdEmpleado(idEmpleado)
                .stream()
                .map(atenderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AtenderDto> obtenerPorVenta(Integer idVenta) {
        return atenderRepository.findByIdVenta(idVenta)
                .stream()
                .map(atenderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AtenderDto crearAtender(AtenderDto atenderDto) {
        AtenderEntity entity = atenderMapper.toEntity(atenderDto);
        AtenderEntity saved = atenderRepository.save(entity);
        return atenderMapper.toDto(saved);
    }
}
