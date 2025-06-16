package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ReservaService {

    List<ReservaDTO> obtenerReservasActivasPorUsuario(Long usuarioId);

    ReservaDTO modificarReserva(Long reservaId, ReservaDTO nuevaReserva);

    ReservaDTO crearReserva(ReservaDTO reserva);

    void cancelarReserva(Long reservaId);

}