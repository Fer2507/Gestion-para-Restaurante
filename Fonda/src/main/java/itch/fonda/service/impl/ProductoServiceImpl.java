package itch.fonda.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import itch.fonda.dto.ProductoDto;
import itch.fonda.entity.ProductoEntity;
import itch.fonda.entity.TipoEntity;
import itch.fonda.mapper.ProductoMapper;
import itch.fonda.repository.ProductoRepository;
import itch.fonda.repository.TipoRepository;
import itch.fonda.service.ProductoService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService{


	private ProductoRepository productoRepository;
	private final TipoRepository tipoRepository;
	
    @Override
    public ProductoDto createProducto(ProductoDto productoDto) {
    	
    	//BUSCAR EL TIPO EN LA BASE DE DATOS, SI NO EXISTE MANDA UNA EXCEPCION
    	TipoEntity tipo = tipoRepository.findById(productoDto.getIdTipo())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + productoDto.getIdTipo()));

        ProductoEntity producto = ProductoMapper.mapToProducto(productoDto, tipo);
    	producto.setActivo(true);
    	ProductoEntity savedProducto = productoRepository.save(producto);
    	
        return ProductoMapper.mapToProductoDto(savedProducto);
    }

    @Override
    public ProductoDto getProductoById(Integer id) {
    	ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return ProductoMapper.mapToProductoDto(producto);
    }
 
    @Override
    public List<ProductoDto> getAllProducto() {
        List<ProductoEntity> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                        .map(ProductoMapper::mapToProductoDto)
                        .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoDto> getAllProductosActivos() {
        List<ProductoEntity> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                        .map(ProductoMapper::mapToProductoDto)
                        .collect(Collectors.toList());
    }

    @Override
    public ProductoDto updateProducto(Integer id, ProductoDto updateProductoDto) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
       
        producto.setNombreProducto(updateProductoDto.getNombreProducto());
        producto.setDescripcionProducto(updateProductoDto.getDescripcionProducto());
        producto.setPrecioProducto(updateProductoDto.getPrecioProducto());

        // SI LLEGA UN idTipo TAMBIEN SE ACTUALIZA
        if (updateProductoDto.getIdTipo() != null) {
            TipoEntity tipo = tipoRepository.findById(updateProductoDto.getIdTipo())
                    .orElseThrow(() -> new RuntimeException("Tipo no encontrado con id: " + updateProductoDto.getIdTipo()));
            producto.setTipo(tipo);
        }
        
        ProductoEntity updatedProducto = productoRepository.save(producto);
        return ProductoMapper.mapToProductoDto(updatedProducto);
    }
//ELIMINAR PRODUCTO DE LA BASE DE DATOS Y LISTA
   @Override
    public void deleteProducto(Integer id) {
    	ProductoEntity producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    	productoRepository.delete(producto);
    }
    //OCULTAR PRODCUTO DE LA LISTA
   @Override
   public void softDeleteProducto(Integer idProducto) {
       ProductoEntity producto = productoRepository.findById(idProducto)
               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
       producto.setActivo(false);
       productoRepository.save(producto);
   }
   //VER DE NUEVO EL PRODUCTO
   @Override
   public void restoreProducto(Integer idProducto) {
       ProductoEntity producto = productoRepository.findById(idProducto)
               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
       producto.setActivo(true);
       productoRepository.save(producto);
   }
//MOSTRAR PODUCTOS OCULTOS DE LA LISTA
   @Override
   public List<ProductoDto> getProductosInactivos() {
       List<ProductoEntity> productos = productoRepository.findByActivoFalse();
       return productos.stream()
               .map(ProductoMapper::mapToProductoDto)
               .collect(Collectors.toList());
   }
   //Imagenes
   @Override
   public void guardarNombreFoto(Integer id, String nombreFoto) {
       ProductoEntity producto = productoRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

       producto.setNombreFoto(nombreFoto);
       productoRepository.save(producto);
   }
   @Override
   public void actualizarNombreImagen(Integer idProducvto, String nombreFoto) {
	   ProductoEntity producto = productoRepository.findById(idProducvto).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
	   producto.setNombreFoto(nombreFoto);
	   productoRepository.save(producto);
   }
   
   //BUSQUEDAS
   @Override
   public List<ProductoDto> buscarNombreContiene(String nombreProducto){
	   List<ProductoEntity> productos = productoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto);
	   return productos.stream()
               .map(ProductoMapper::mapToProductoDto)
               .collect(Collectors.toList());
   }
   
   @Override
   public List<ProductoDto> buscarPorTipo(String nombreTipo) {
	    List<ProductoEntity> productos = productoRepository.findByTipoNombreTipoContainingIgnoreCase(nombreTipo);
	    return productos.stream()
           .map(ProductoMapper::mapToProductoDto)
           .collect(Collectors.toList());
   }

	@Override
	public List<ProductoDto> buscarPrecioEntre(Double min, Double max) {
	   List<ProductoEntity> productos = productoRepository.findByPrecioProductoBetween(min, max);
	   return productos.stream()
               .map(ProductoMapper::mapToProductoDto)
               .collect(Collectors.toList());
	}

}
