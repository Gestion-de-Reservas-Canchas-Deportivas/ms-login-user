package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrecioDTO {

    private Long id;

    private Long canchaId;

    private Long horarioId;

    private BigDecimal precio;
}
