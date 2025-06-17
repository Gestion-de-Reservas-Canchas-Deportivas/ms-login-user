package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReporteExportServiceTest {

    private final ReporteExportService exportService = new ReporteExportService();

    @Test
    void generarExcel_deberiaRetornarBytes() {
        ReporteReservaDTO dto = new ReporteReservaDTO();
        dto.setNombreCancha("Cancha 1");
        dto.setTipoCancha("Fútbol");
        dto.setTotalReservas(5L);
        dto.setHorasUtilizadas(10);
        dto.setPorcentajeOcupacion(83.3);

        byte[] resultado = exportService.generarExcel(List.of(dto));

        assertNotNull(resultado);
        assertTrue(resultado.length > 0, "El contenido del Excel no debería estar vacío");
    }

    @Test
    void generarPdf_deberiaRetornarBytes() {
        ReporteReservaDTO dto = new ReporteReservaDTO();
        dto.setNombreCancha("Cancha 1");
        dto.setTipoCancha("Fútbol");
        dto.setTotalReservas(5L);
        dto.setHorasUtilizadas(10);
        dto.setPorcentajeOcupacion(83.3);

        byte[] resultado = exportService.generarPdf(List.of(dto));

        assertNotNull(resultado);
        assertTrue(resultado.length > 0, "El contenido del PDF no debería estar vacío");
    }
}
