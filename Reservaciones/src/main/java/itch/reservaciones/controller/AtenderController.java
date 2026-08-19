package itch.reservaciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import itch.reservaciones.dto.AtenderDto;
import itch.reservaciones.service.AtenderService;

@RestController
@RequestMapping("/api/atender")
public class AtenderController {

    private final AtenderService atenderService;

    public AtenderController(AtenderService atenderService) {
        this.atenderService = atenderService;
    }
    
    @PreAuthorize("hasAuthority('GESTIONAR_PEDIDOS') or hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<AtenderDto>> listarTodos() {
        List<AtenderDto> lista = atenderService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAuthority('GESTIONAR_PEDIDOS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<AtenderDto>> obtenerPorEmpleado(@PathVariable Integer idEmpleado) {
        List<AtenderDto> lista = atenderService.obtenerPorEmpleado(idEmpleado);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }


    @PreAuthorize("hasAuthority('GESTIONAR_PEDIDOS') or hasRole('ADMINISTRADOR')")
    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<AtenderDto>> obtenerPorVenta(@PathVariable Integer idVenta) {
        List<AtenderDto> lista = atenderService.obtenerPorVenta(idVenta);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAuthority('GESTIONAR_PEDIDOS') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<AtenderDto> crearAtender(@RequestBody AtenderDto atenderDto) {
        AtenderDto creado = atenderService.crearAtender(atenderDto);
        return ResponseEntity.ok(creado);
    }
}
