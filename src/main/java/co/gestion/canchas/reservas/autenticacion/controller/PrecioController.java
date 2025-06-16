package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.service.PrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-cancha/{canchaId}/horarios/{horarioId}/precios")
@RequiredArgsConstructor
public class PrecioController {

    private final PrecioService service;

    /**
     * Crea un precio para la cancha+horario
     */
    @PostMapping
    public ResponseEntity<PrecioDTO> create(
            @PathVariable Long canchaId,
            @PathVariable Long horarioId,
            @RequestBody PrecioDTO dto) {
        dto.setCanchaId(canchaId);
        dto.setHorarioId(horarioId);
        PrecioDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Lista precios de una cancha (todas las franjas)
     */
    @GetMapping
    public ResponseEntity<List<PrecioDTO>> listByCancha(@PathVariable Long canchaId) {
        return ResponseEntity.ok(service.findByCancha(canchaId));
    }

    /**
     * Lista precios de una franja
     */
    @GetMapping("/franja")
    public ResponseEntity<List<PrecioDTO>> listByHorario(@PathVariable Long horarioId) {
        return ResponseEntity.ok(service.findByHorario(horarioId));
    }

    /**
     * Actualiza un precio existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrecioDTO> update(
            @PathVariable Long canchaId,
            @PathVariable Long horarioId,
            @PathVariable Long id,
            @RequestBody PrecioDTO dto) {
        dto.setCanchaId(canchaId);
        dto.setHorarioId(horarioId);
        PrecioDTO upd = service.update(id, dto);
        return ResponseEntity.ok(upd);
    }

    /**
     * Elimina un precio
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}