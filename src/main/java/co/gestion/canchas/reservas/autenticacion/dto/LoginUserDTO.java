package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Data;

/**
 * DTO que representa los datos necesarios para el inicio de sesión de un usuario.
 * <p>
 * Este objeto contiene las credenciales del usuario, como el correo electrónico
 * y la contraseña, requeridas para realizar la autenticación en el sistema.
 * </p>
 */
@Data
public class LoginUserDTO {

    private String emailAddress;

    private String password;
}
