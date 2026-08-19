package itch.delacruz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto {
	private Integer idUsuario;
    private String username;
    private String password;
    private String email;
    private Boolean estado;
    private String rol;

    public UsuarioDto(Integer idUsuario, String username, String password, String email, Boolean estado, String rol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.estado = estado;
        this.rol = rol;
    }

}
