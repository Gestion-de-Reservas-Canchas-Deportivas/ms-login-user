package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;
import co.gestion.canchas.reservas.autenticacion.service.FacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService facturacionService;

    @GetMapping
    public ResponseEntity<?> obtenerResumenFacturacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(required = false) Long tipoCanchaId,
            @RequestParam(required = false) Long usuarioId
    ) {
        List<FacturacionDTO> resumen = facturacionService.obtenerResumen(inicio, fin, tipoCanchaId, usuarioId);

        BigDecimal total = resumen.stream()
                .map(FacturacionDTO::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(new FacturacionResponse(resumen, total));
    }

    public record FacturacionResponse(List<FacturacionDTO> resumen, BigDecimal totalFacturado) {
    }
}
