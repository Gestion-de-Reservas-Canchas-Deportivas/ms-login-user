package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class HorarioDTO {

    private Long id;

    private Long canchaId;

    private DayOfWeek diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFin;
}
