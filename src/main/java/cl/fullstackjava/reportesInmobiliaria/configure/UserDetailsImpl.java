/**
 * Implementación de la interfaz UserDetails para cargar los detalles de los usuarios desde la base de datos.
 *
 * - Carga los roles y permisos del usuario.
 * - Utilizado por Spring Security para la autenticación y autorización.
 */
package cl.fullstackjava.reportesInmobiliaria.configure;

import cl.fullstackjava.reportesInmobiliaria.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
