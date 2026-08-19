package itch.fonda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoDto {

	private Integer id;
    private String nombreTipo;
    private String descripcionTipo;
}
