package co.gestion.canchas.reservas.autenticacion.utils.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
