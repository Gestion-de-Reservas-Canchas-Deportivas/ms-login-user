package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteReservaService {

    /**
     * Genera un reporte con el total de reservas por cancha y tipo, dentro de un rango de fechas,
     * opcionalmente filtrado por tipo de cancha y/o cancha específica.
     *
     * @param fechaInicio  Fecha inicial del rango
     * @param fechaFin     Fecha final del rango
     * @param tipoCanchaId (opcional) ID del tipo de cancha a filtrar
     * @return Lista de datos consolidados para el reporte
     */
    List<ReporteReservaDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long tipoCanchaId);
}