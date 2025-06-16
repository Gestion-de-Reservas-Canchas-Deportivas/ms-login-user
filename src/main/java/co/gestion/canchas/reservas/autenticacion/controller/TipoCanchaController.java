package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.TipoCanchaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ITipoCanchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipo-cancha")
@RequiredArgsConstructor
public class TipoCanchaController {

    private final ITipoCanchaService service;

    /**
     * Crea un nuevo tipo de cancha
     */
    @PostMapping("/save")
    public ResponseEntity<TipoCanchaDTO> create(@RequestBody TipoCanchaDTO dto) {
        TipoCanchaDTO created = service.createTipoCancha(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Trae todos los tipos de cancha
     */
    @GetMapping("/all")
    public ResponseEntity<List<TipoCanchaDTO>> getAll() {
        List<TipoCanchaDTO> list = service.fetchAllTipos();
        return ResponseEntity.ok(list);
    }

    /**
     * Trae un tipo de cancha por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoCanchaDTO> getById(@PathVariable Long id) {
        TipoCanchaDTO dto = service.fetchTipoById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Actualiza un tipo de cancha existente
     */
    @PutMapping("/updateCancha/{id}")
    public ResponseEntity<TipoCanchaDTO> update(
            @PathVariable Long id,
            @RequestBody TipoCanchaDTO dto) {
        TipoCanchaDTO updated = service.updateTipoCancha(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un tipo de cancha
     */
    @DeleteMapping("/deleteCanchaBy/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        boolean deleted = service.deleteTipoCancha(id);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted", deleted);
        return ResponseEntity.ok(resp);
    }
}
