package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ReporteReservaService;
import co.gestion.canchas.reservas.autenticacion.service.impl.ReporteExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteReservaControllerTest {

    @InjectMocks
    private ReporteReservaController controller;

    @Mock
    private ReporteReservaService reporteReservaService;

    @Mock
    private ReporteExportService exportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerReporte() {
        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fin = LocalDate.now();
        Long tipoCanchaId = 1L;

        List<ReporteReservaDTO> reporte = List.of(new ReporteReservaDTO(), new ReporteReservaDTO());
        when(reporteReservaService.generarReporte(inicio, fin, tipoCanchaId)).thenReturn(reporte);

        ResponseEntity<List<ReporteReservaDTO>> response = controller.obtenerReporte(inicio, fin, tipoCanchaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reporte, response.getBody());
        verify(reporteReservaService).generarReporte(inicio, fin, tipoCanchaId);
    }

    @Test
    void testDescargarExcel() {
        LocalDate inicio = LocalDate.now().minusDays(3);
        LocalDate fin = LocalDate.now();
        Long tipoCanchaId = null;

        List<ReporteReservaDTO> datos = List.of(new ReporteReservaDTO());
        byte[] excelBytes = "fakeExcelContent".getBytes();

        when(reporteReservaService.generarReporte(inicio, fin, tipoCanchaId)).thenReturn(datos);
        when(exportService.generarExcel(datos)).thenReturn(excelBytes);

        ResponseEntity<byte[]> response = controller.descargarExcel(inicio, fin, tipoCanchaId);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(excelBytes, response.getBody());
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                response.getHeaders().getContentType().toString());
    }

    @Test
    void testDescargarPdf() {
        LocalDate inicio = LocalDate.now().minusDays(1);
        LocalDate fin = LocalDate.now();
        Long tipoCanchaId = 5L;

        List<ReporteReservaDTO> datos = List.of(new ReporteReservaDTO());
        byte[] pdfBytes = "fakePdfContent".getBytes();

        when(reporteReservaService.generarReporte(inicio, fin, tipoCanchaId)).thenReturn(datos);
        when(exportService.generarPdf(datos)).thenReturn(pdfBytes);

        ResponseEntity<byte[]> response = controller.descargarPdf(inicio, fin, tipoCanchaId);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(pdfBytes, response.getBody());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
    }
}
