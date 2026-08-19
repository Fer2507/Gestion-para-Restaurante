package itch.fonda.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import itch.fonda.dto.ProductoDto;
import itch.fonda.service.ProductoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/producto")
public class ProductoController {
	@Autowired
	 private ProductoService productoService;

	    // POST: Crear Producto
		@PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @PostMapping()
	    public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto ProductoDto) {
	        ProductoDto guardarProducto = productoService.createProducto(ProductoDto);
	        return new ResponseEntity<>(guardarProducto, HttpStatus.CREATED);
	    }

	    // GET: Obtener Producto por ID
		@PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @GetMapping("/{id}")
	    public ResponseEntity<ProductoDto> getProductoById(@PathVariable("id") Integer id) {
	    	ProductoDto ProductoDto = productoService.getProductoById(id);
	        return ResponseEntity.ok(ProductoDto);
	    }

	    // GET: Obtener todos los Producto
		@PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @GetMapping
	    public ResponseEntity<List<ProductoDto>> getAllProductos() { List<ProductoDto> Productos = productoService.getAllProducto();
	        return ResponseEntity.ok(Productos);
	    }
		// GET: Obtener todos los Producto
		@PreAuthorize("permitAll()")
		@GetMapping("/activos")
		public ResponseEntity<List<ProductoDto>> getAllProductosActivos() { List<ProductoDto> Productos = productoService.getAllProducto();
			 return ResponseEntity.ok(Productos);
		}

	    // PUT: Actualizar Producto
		@PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @PutMapping("{id}")
	    public ResponseEntity<ProductoDto> updateProducto(@PathVariable("id") Integer ProductoId,
	                                                    @RequestBody ProductoDto updateProductoDto) {
	    	ProductoDto ProductoDto = productoService.updateProducto(ProductoId, updateProductoDto);
	        return ResponseEntity.ok(ProductoDto);
	    }

	    // DELETE: Eliminar Producto
	   @PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @DeleteMapping("{id}")
	    public ResponseEntity<String> deleteProducto(@PathVariable("id") Integer id) {
	    	productoService.deleteProducto(id);
	        return ResponseEntity.ok("Registro eliminado");
	    }
	    
	   // ELIMINAR DE LA LISTA
	    @PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @PutMapping("/ocultar/{id}")
	    public ResponseEntity<String> ocultarProducto(@PathVariable Integer id) {
	        productoService.softDeleteProducto(id);
	        return ResponseEntity.ok("Producto Ocultado correctamente");
	    }

	    // MOSTRAR PRODUCTOS OCULTOS DE LA LISTA
	    @PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @GetMapping("/inactivos")
	    public ResponseEntity<List<ProductoDto>> listarInactivos() {
	        return ResponseEntity.ok(productoService.getProductosInactivos());
	    }
	    
	    @PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    @PutMapping("/activar/{id}")
	    public ResponseEntity<String> restaurarProducto(@PathVariable Integer id) {
	        productoService.restoreProducto(id);
	        return ResponseEntity.ok("Producto restaurado correctamente");
	    }
	    
	    @PreAuthorize("hasAuthority('GESTIONAR_PRODUCTOS') or hasRole('ADMINISTRADOR')")
	    //Subir Imagen
	    @PostMapping("/uploadImage/{id}")
	    public ResponseEntity<String> uploadImage(
	            @PathVariable Integer id,
	            @RequestParam("imagen") MultipartFile imagen) {

	        try {
	        	System.out.println("Recibiendo archivo para producto ID: " + id);
	        	System.out.println("Nombre del archivo recibido: " + (imagen != null ? imagen.getOriginalFilename() : "null"));

	            if (imagen.isEmpty()) {
	                return ResponseEntity.badRequest().body("Archivo vacío");
	            }

	            String carpeta = "C:/Users/ferna/Pictures/Productos Taller/";
	           
	            String nombreArchivo = id + "_" + imagen.getOriginalFilename();
	           
	            Path ruta = Paths.get(carpeta + nombreArchivo);

	            Files.copy(imagen.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

	            // Guardar nombre en BD
	            productoService.guardarNombreFoto(id, nombreArchivo);

	            return ResponseEntity.ok("Imagen subida correctamente");
	        } catch (Exception e) {
	        	 e.printStackTrace();
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                     .body("Error al guardar la imagen: " + e.getMessage());
	        }
	    }
	    
	    //Ver Imagen
	    @PreAuthorize("permitAll()")
	    @GetMapping("/img/{nombreFoto}")
	    public ResponseEntity<Resource> verImagen(@PathVariable String nombreFoto){
	    	try {
	    		 // Si viene null, vacío o "null", asignar imagen default
	            if (nombreFoto == null || nombreFoto.equals("null") || nombreFoto.isEmpty()) {
	                nombreFoto = "no_imagen.jpg";
	            }
	            
	    		Path ruta = Paths.get("C:/Users/ferna/Pictures/Productos Taller/").resolve(nombreFoto);
	    		Resource recurso = new UrlResource(ruta.toUri());
	    		
	    		if (!recurso.exists()) {
	                Path rutaDefault = Paths.get("C:/Users/ferna/Pictures/Productos Taller/").resolve("no_imagen.jpg");
	                recurso = new UrlResource(rutaDefault.toUri());
	            }
	    		String tipoImg = Files.probeContentType(ruta);
	    		if(tipoImg == null) {
	    			tipoImg = "application/octet-stream";
	    		}
	    		return ResponseEntity.ok().contentType(MediaType.parseMediaType(tipoImg)).body(recurso);
	    	
	    	}catch(Exception e) {
	    		return ResponseEntity.internalServerError().build();
	    	}
	    }
	    
	    //BUSQUEDAS
	    @PreAuthorize("permitAll()")
	    @GetMapping("/buscar")
	    public ResponseEntity<List<ProductoDto>> buscarProducto(@RequestParam("nombreProducto") String nombreProducto) {
	        List<ProductoDto> productos = productoService.buscarNombreContiene(nombreProducto);
	        return ResponseEntity.ok(productos);
	    }
	    
	    @PreAuthorize("permitAll()")
	    @GetMapping("/buscarPorTipo")
	    public ResponseEntity<List<ProductoDto>> buscarPorTipobuscarPorNombreTipo(@RequestParam String nombreTipo) {
	        List<ProductoDto> productos = productoService.buscarPorTipo(nombreTipo);
	        return ResponseEntity.ok(productos);
	    }

	    // 🔹 Buscar entre precios
	    @PreAuthorize("permitAll()")
	    @GetMapping("/buscarEntrePrecios")
	    public ResponseEntity<List<ProductoDto>> buscarEntrePrecios(
	            @RequestParam Double min,
	            @RequestParam Double max) {
	        List<ProductoDto> productos = productoService.buscarPrecioEntre(min, max);
	        return ResponseEntity.ok(productos);
	    }
	    
	    
}

