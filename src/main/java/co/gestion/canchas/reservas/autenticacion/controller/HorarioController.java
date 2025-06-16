package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-cancha/{canchaId}/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService service;

    @PostMapping
    public ResponseEntity<HorarioDTO> create(
            @PathVariable Long canchaId,
            @RequestBody HorarioDTO dto) {
        dto.setCanchaId(canchaId);
        HorarioDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HorarioDTO>> list(
            @PathVariable Long canchaId) {
        List<HorarioDTO> list = service.findByCancha(canchaId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/updateHorario/{id}")
    public ResponseEntity<HorarioDTO> update(
            @PathVariable Long canchaId,
            @PathVariable Long id,
            @RequestBody HorarioDTO dto) {
        dto.setCanchaId(canchaId);
        HorarioDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteHorarioBy/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long canchaId,
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}