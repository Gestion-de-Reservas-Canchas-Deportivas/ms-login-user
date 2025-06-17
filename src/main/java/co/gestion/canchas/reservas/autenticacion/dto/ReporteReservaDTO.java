package co.gestion.canchas.reservas.autenticacion.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReporteReservaDTO {

    private String nombreCancha;

    private String tipoCancha;

    private Long totalReservas;

    private int horasUtilizadas;

    private double porcentajeOcupacion;

    public ReporteReservaDTO(String nombreCancha, String tipoCancha, Long totalReservas, int horasUtilizadas, double porcentajeOcupacion) {
        this.nombreCancha = nombreCancha;
        this.tipoCancha = tipoCancha;
        this.totalReservas = totalReservas;
        this.horasUtilizadas = horasUtilizadas;
        this.porcentajeOcupacion = porcentajeOcupacion;
    }
}
