package itch.reservaciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itch.reservaciones.dto.MesaDto;
import itch.reservaciones.service.MesaService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/mesa")
@PreAuthorize("hasRole('CAJERO') or hasRole('ADMINISTRADOR')")
public class MesaController {

    @Autowired
    private MesaService mesaService;

 // Obtener todas las mesas
    @GetMapping
    public ResponseEntity<List<MesaDto>> getAllMesas() {
        List<MesaDto> mesas = mesaService.getAllMesas();
        return ResponseEntity.ok(mesas);
    }

    // Obtener mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<MesaDto> getMesaById(@PathVariable("id") Integer id) {
        MesaDto mesaDto = mesaService.getMesaById(id);
        return ResponseEntity.ok(mesaDto);
    }

    // Obtener mesa por número
    @GetMapping("/numero/{numero}")
    public ResponseEntity<MesaDto> getMesaByNumero(@PathVariable("numero") Integer numero) {
        MesaDto mesaDto = mesaService.getMesaByNumero(numero);
        return ResponseEntity.ok(mesaDto);
    }
 // Crear nueva mesa
    @PostMapping
    public ResponseEntity<MesaDto> crearMesa(@RequestBody MesaDto mesaDto) {
        MesaDto nueva = mesaService.guardarMesa(mesaDto);
        return ResponseEntity.ok(nueva);
    }

    // Actualizar mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<MesaDto> actualizarMesa(@PathVariable Integer id, @RequestBody MesaDto mesaDto) {
        MesaDto actualizada = mesaService.actualizarMesa(id, mesaDto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar mesa
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMesa(@PathVariable Integer id) {
        mesaService.eliminarMesa(id);
        return ResponseEntity.ok("Mesa eliminada con éxito (ID: " + id + ")");
    }
}

