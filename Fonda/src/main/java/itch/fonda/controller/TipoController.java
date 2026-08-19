package itch.fonda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import itch.fonda.dto.TipoDto;
import itch.fonda.entity.ProductoEntity;
import itch.fonda.entity.TipoEntity;
import itch.fonda.repository.TipoRepository;
import itch.fonda.service.TipoService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tipo")
@PreAuthorize("hasAuthority('GESTIONAR_TIPOS') or hasRole('ADMINISTRADOR')")
public class TipoController {
	@Autowired
	 private TipoService tipoService;
	@Autowired
	 private TipoRepository tipoRepositori;

	    // POST: Crear Tipo
	    @PostMapping
	    public ResponseEntity<TipoDto> createProducto(@RequestBody TipoDto TipoDto) {
	    	TipoDto guardarTipo = tipoService.createTipo(TipoDto);
	        return new ResponseEntity<>(guardarTipo, HttpStatus.CREATED);
	    }

	    // GET: Obtener Tipo por ID
	    
	    @GetMapping("{id}")
	    public ResponseEntity<TipoDto> getTipoById(@PathVariable("id") Integer id) {
	    	TipoDto TipoDto = tipoService.getTipoById(id);
	        return ResponseEntity.ok(TipoDto);
	    }

	    // GET: Obtener todos los Tipo
	    @GetMapping
	    public ResponseEntity<List<TipoDto>> getAllTipo() { List<TipoDto> Tipos = tipoService.getAllTipo();
	        return ResponseEntity.ok(Tipos); 
	    }
	    //GET: PARA VISUALIZAR LOS PRODUCTOS ASOSCIADOS AL TIPO
	    @GetMapping("/{id}/productos")
	    public ResponseEntity<List<ProductoEntity>> obtenerProductosPorTipo(@PathVariable Integer id) {
	        TipoEntity tipo = tipoRepositori.findById(id)
	                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

	        return ResponseEntity.ok(tipo.getProductos());
	    }

	    // PUT: Actualizar Tipo
	    @PutMapping("{id}")
	    public ResponseEntity<TipoDto> updateTipo(@PathVariable("id") Integer TipoId,
	                                                    @RequestBody TipoDto updateTipoDto) {
	    	TipoDto TipoDto = tipoService.updateTipo(TipoId, updateTipoDto);
	        return ResponseEntity.ok(TipoDto);
	    }

	    // DELETE: Eliminar Tipo
	    @DeleteMapping("{id}")
	    public ResponseEntity<String> deleteTipo(@PathVariable("id") Integer id) {
	    	tipoService.deleteTipo(id);
	        return ResponseEntity.ok("Registro eliminado");
	    }
}

