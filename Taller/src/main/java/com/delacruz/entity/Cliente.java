package com.delacruz.entity;

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
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idCliente; 

	@Column(name = "nombre")
    private String nombreCliente;

    @Column(name = "telefono")
    private String telefonoCliente;

    @Column(name = "correo")
    private String correoCliente;
    
    @Column(name = "clave")
    private String clave;
    
    @Column(name = "usuario_id")
	private Integer idUsuario;
    
}
