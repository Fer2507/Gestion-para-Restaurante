package itch.fonda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Producto")

public class ProductoEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_producto")
	    private Integer idProducto;

	    @Column(name = "nombre")
	    private String nombreProducto;

	    @Column(name = "descripcion")
	    private String descripcionProducto;

	    @Column(name = "precio")
	    private Double precioProducto;

	    @ManyToOne
	    @JoinColumn(name = "id_Tipo") //COLUMNA QUE CONECTA CON TIPO
	    private TipoEntity tipo;
	    
	    @Column(name = "activo")
	    private Boolean activo;
	    
	    @Column(name = "nombre_Foto")
	    private String nombreFoto;
}
