package itch.delacruz.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolDto {
	private Integer idRol;
    private String nombre;
    private String descripcion;
    private Set<PermisoDto> permisos;
    
    public RolDto(Integer idRol, String nombre, String descripcion, Set<PermisoDto> permisos) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = permisos;
    }
}
