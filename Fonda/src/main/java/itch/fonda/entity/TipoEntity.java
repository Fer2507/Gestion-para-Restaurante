package itch.fonda.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "Tipo")
public class TipoEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;
	
	@Column(name = "tipo")
    private String nombreTipo;

    @Column(name = "descripcion")
    private String descripcionTipo;
    
    public TipoEntity(Integer id, String nombreTipo, String descripcionTipo) {
        this.id = id;
        this.nombreTipo = nombreTipo;
        this.descripcionTipo = descripcionTipo;
    }

    
 // RELACION INVERSA
    @OneToMany(mappedBy = "tipo")
    private List<ProductoEntity> productos;
}
