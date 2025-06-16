package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservaDTO {

    private Long id;

    private Long usuarioId;

    private Long canchaId;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;
}
