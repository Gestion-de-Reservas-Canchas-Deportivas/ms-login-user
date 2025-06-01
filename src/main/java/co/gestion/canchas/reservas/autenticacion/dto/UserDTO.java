package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para representar la información de un usuario.
 * <p>
 * Contiene los datos generales del usuario, incluyendo identificadores,
 * información personal y credenciales.
 * </p>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phone;

    private String address;

    private LocalDate birthDate;

    private String hashPassword;

    private String password;
}
