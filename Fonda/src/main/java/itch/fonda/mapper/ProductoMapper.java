package itch.fonda.mapper;

import itch.fonda.dto.ProductoDto;
import itch.fonda.entity.ProductoEntity;
import itch.fonda.entity.TipoEntity;


public class ProductoMapper { 

    public static ProductoDto mapToProductoDto(ProductoEntity productoEntity) {
    	 String nombreFoto = productoEntity.getNombreFoto();

    	    // Si no hay imagen, asignar una por defecto
    	    if (nombreFoto == null || nombreFoto.trim().isEmpty()) {
    	        nombreFoto = "no_imagen.jpg";
    	    }
        return new ProductoDto( 
        		productoEntity.getIdProducto(),
        	    productoEntity.getNombreProducto(),
        	    productoEntity.getDescripcionProducto(),
        	    productoEntity.getPrecioProducto(),
        	    productoEntity.getTipo() != null ? productoEntity.getTipo().getId() : null,
        	    productoEntity.getTipo() != null ? productoEntity.getTipo().getNombreTipo() : null,
        	    productoEntity.getActivo(),
        	    productoEntity.getNombreFoto(),
        	    null // fotoproducto inicializado vacío
        );
    }

    public static ProductoEntity mapToProducto(ProductoDto productoDto, TipoEntity tipoEntity) {
        return new ProductoEntity(
            productoDto.getIdProducto(),
            productoDto.getNombreProducto(),
            productoDto.getDescripcionProducto(),
            productoDto.getPrecioProducto(),
            //SE ASIGNA COMPLETAMENTE EL TipoEntity
            tipoEntity, 
            productoDto.getActivo() != null ? productoDto.getActivo() : true,
            productoDto.getNombreFoto()
        );
    }
}
