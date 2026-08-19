package itch.fonda.service;
import java.util.List;

import itch.fonda.dto.ProductoDto;

public interface ProductoService {
	ProductoDto createProducto (ProductoDto productoDto);
		
	//OCULTOS
    void softDeleteProducto(Integer idProducto);
    //Ver de Nuevo el producto
    void restoreProducto(Integer idProducto); 
    
    //LISTA DE PRODUCTOS INACTIVOS
    List<ProductoDto> getProductosInactivos();
	
	//BUSCAR Producto POR ID
	ProductoDto getProductoById(Integer Id);
	
	//OBTENER TODOS LOS Producto
	List<ProductoDto> getAllProducto();
	
	//OBTENER TODOS LOS Producto Activos
	List<ProductoDto> getAllProductosActivos();
	
	//CONTRUIR REST API UPDATE Producto
	ProductoDto updateProducto(Integer Id, ProductoDto updateProducto);
	
	//CONSTRUIR DELETE REST API Producto
	void deleteProducto(Integer Id); 
	
	//Guardar Imagen
	void guardarNombreFoto(Integer id, String nombreFoto);
	
	//Actualizar foto
	void actualizarNombreImagen(Integer IdProducto, String nombreFoto);
	
	//BUSQUEDAS
	List<ProductoDto> buscarPrecioEntre(Double min, Double max);
	List<ProductoDto> buscarPorTipo(String nombreTipo);
	List<ProductoDto> buscarNombreContiene(String nombreProducto);
}
