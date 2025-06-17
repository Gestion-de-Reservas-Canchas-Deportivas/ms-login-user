package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ReporteReservaService;
import co.gestion.canchas.reservas.autenticacion.service.impl.ReporteExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes/uso-canchas")
@RequiredArgsConstructor
public class ReporteReservaController {

    private final ReporteReservaService reporteReservaService;
    private final ReporteExportService exportService;

    @GetMapping
    public ResponseEntity<List<ReporteReservaDTO>> obtenerReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Long tipoCanchaId
    ) {
        List<ReporteReservaDTO> reporte = reporteReservaService.generarReporte(fechaInicio, fechaFin, tipoCanchaId);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> descargarExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Long tipoCanchaId
    ) {
        List<ReporteReservaDTO> datos = reporteReservaService.generarReporte(fechaInicio, fechaFin, tipoCanchaId);
        byte[] excel = exportService.generarExcel(datos);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_uso_canchas.xlsx")
                .body(excel);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Long tipoCanchaId
    ) {
        List<ReporteReservaDTO> datos = reporteReservaService.generarReporte(fechaInicio, fechaFin, tipoCanchaId);
        byte[] pdf = exportService.generarPdf(datos);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_uso_canchas.pdf")
                .body(pdf);
    }

}