package itch.delacruz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDto {
	private Integer idPermiso;
    private String nombre;
    private String descripcion;
}
