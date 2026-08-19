package itch.fonda.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
	private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer idTipo; // REFERENCIA A TIPO
    private String nombreTipo;
    private Boolean activo;
    private String nombreFoto;
    private MultipartFile fotoproducto;
    
    
}
