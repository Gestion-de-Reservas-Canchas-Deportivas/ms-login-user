package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorreoDto {

    private String to;

    private String subject;

    private String text;

}
