package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO dto) {
        ReservaDTO creada = reservaService.crearReserva(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/activas/{usuarioId}")
    public List<ReservaDTO> obtenerActivas(@PathVariable Long usuarioId) {
        return reservaService.obtenerReservasActivasPorUsuario(usuarioId);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Map<String, String>> modificarReserva(@RequestBody ReservaDTO dto) {
        reservaService.modificarReserva(dto.getId(), dto);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Reserva actualizada con éxito");

        return ResponseEntity.ok(response); // ✅ Esto ya es JSON
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Map<String, String>> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.ok(Map.of("mensaje", "Reserva cancelada con éxito"));
    }

}


