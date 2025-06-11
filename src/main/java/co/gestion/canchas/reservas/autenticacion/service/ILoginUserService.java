package co.gestion.canchas.reservas.autenticacion.service;


import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;

/**
 * Interfaz para el servicio de autenticación de usuarios.
 * <p>
 * Define el contrato para realizar el proceso de inicio de sesión
 * de un usuario en el sistema.
 * </p>
 */
public interface ILoginUserService {

    /**
     * Realiza el inicio de sesión de un usuario.
     *
     * @param loginUserDTO el objeto {@link LoginUserDTO} que contiene las credenciales
     *                     del usuario (correo y contraseña).
     * @return un objeto {@link RespuestaGeneralDTO} que contiene el estado, mensaje y datos
     * del resultado de la operación, incluyendo un token de autenticación si el
     * inicio de sesión es exitoso.
     */
    RespuestaGeneralDTO loginUser(LoginUserDTO loginUserDTO);
}
