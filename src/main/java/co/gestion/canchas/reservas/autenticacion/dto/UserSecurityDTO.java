package co.gestion.canchas.reservas.autenticacion.dto;

import co.gestion.canchas.reservas.autenticacion.utils.constants.Constantes;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Clase que representa los detalles de seguridad de un usuario para la autenticación y autorización.
 */
@Data
public class UserSecurityDTO extends UserDTO implements UserDetails {

    /**
     * Contraseña del usuario utilizada para la autenticación.
     */
    private String password;

    public UserSecurityDTO(UserDTO userDTO) {
        super();
        setId(userDTO.getId());
        setFirstName(userDTO.getFirstName());
        setLastName(userDTO.getLastName());
        setEmailAddress(userDTO.getEmailAddress());
        setPhone(userDTO.getPhone());
        setAddress(userDTO.getAddress());
        setBirthDate(userDTO.getBirthDate());
        this.password = userDTO.getHashPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Constantes.ROL_USER));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
