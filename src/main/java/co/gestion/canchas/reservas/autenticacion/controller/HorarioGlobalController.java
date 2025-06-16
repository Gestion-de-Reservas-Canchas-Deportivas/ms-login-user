package co.gestion.canchas.reservas.autenticacion.controller;
import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class HorarioGlobalController {

    private final HorarioService service;

    /**
     * Obtiene todos los horarios
     */
    @GetMapping("/all")
    public ResponseEntity<List<HorarioDTO>> listAll() {
        List<HorarioDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
