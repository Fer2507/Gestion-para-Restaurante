package itch.delacruz.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import itch.delacruz.entity.Usuario;
import itch.delacruz.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    /* Carga un usuario por su username y lo convierte en un objeto que Spring Security pueda manejar. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Extraer los permisos del rol
        List<SimpleGrantedAuthority> authorities = usuario.getRol().getPermisos()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getNombre()))
                .collect(Collectors.toList());

        // 3Crear y devolver un objeto de tipo User
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEstado(), // habilitado o no
                true,  // cuenta
                true,  // credenciales 
                true,  // cuenta no bloqueada
                authorities // lista de permisos
        );
    }
}

