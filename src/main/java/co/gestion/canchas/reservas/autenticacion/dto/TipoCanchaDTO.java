package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TipoCanchaDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean activo;

}
