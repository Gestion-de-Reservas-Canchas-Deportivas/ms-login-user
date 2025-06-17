package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {

    private Long id;

    private Long canchaId;

    private DayOfWeek diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    public HorarioDTO(Long id, long canchaId, int i, LocalTime of, LocalTime of1) {
    }
}
