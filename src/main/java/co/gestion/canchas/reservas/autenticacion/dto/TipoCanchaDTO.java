package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TipoCanchaDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean activo;

}
