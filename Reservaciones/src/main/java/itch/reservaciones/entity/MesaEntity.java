package itch.reservaciones.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Mesa")
public class MesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_Mesa")
    private Integer idMesa;       
    
    @Column(name = "numero")
    private Integer numero;    
    
    @Column(name = "capacidad")
    private Integer capacidad; 
    
    @Column(name = "ubicacion")
    private String ubicacion;
    
    @Column(name = "estado")
    private String estado;
}
