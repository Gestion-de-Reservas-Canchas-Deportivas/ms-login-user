package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioDTO {

    private Long id;

    private Long canchaId;

    private Long horarioId;

    private BigDecimal precio;
}
