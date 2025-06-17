package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FacturacionDTO {

    private Long reservaId;

    private Long usuarioId;

    private String tipoCancha;

    private LocalDate fecha;

    private String horaInicio;

    private String horaFin;

    private BigDecimal monto;

    public FacturacionDTO(Long reservaId, Long usuarioId, String tipoCancha, LocalDate fecha, String horaInicio, String horaFin, BigDecimal monto) {
        this.reservaId = reservaId;
        this.usuarioId = usuarioId;
        this.tipoCancha = tipoCancha;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monto = monto;
    }

    public FacturacionDTO() {

    }
}
