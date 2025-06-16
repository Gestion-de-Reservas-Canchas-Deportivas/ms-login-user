package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaRepository;
import co.gestion.canchas.reservas.autenticacion.service.ReservaService;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.ReservaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;


    @Override
    public List<ReservaDTO> obtenerReservasActivasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioIdAndActivaTrue(usuarioId).stream()
                .map(reservaMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        try {
            var conflictos = reservaRepository.findConflictos(
                    reservaDTO.getCanchaId(),
                    reservaDTO.getFecha(),
                    reservaDTO.getHoraInicio(),
                    reservaDTO.getHoraFin(),
                    -1L
            );

            if (!conflictos.isEmpty()) {
                throw new RuntimeException("El horario ya está reservado.");
            }

            ReservaEntity nuevaReserva = reservaMapper.dtoToEntity(reservaDTO);
            nuevaReserva.setActiva(true);
            ReservaEntity guardada = reservaRepository.save(nuevaReserva);

            return reservaMapper.entityToDto(guardada);
        } catch (Exception e) {
            log.error("Error al crear la reserva: {}", e.getMessage(), e);
            throw e; // o lanzar una excepción personalizada
        }

    }


    @Override
    public ReservaDTO modificarReserva(Long reservaId, ReservaDTO nuevaReserva) {
        ReservaEntity reservaExistente = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        var conflictos = reservaRepository.findConflictos(
                nuevaReserva.getCanchaId(),
                nuevaReserva.getFecha(),
                nuevaReserva.getHoraInicio(),
                nuevaReserva.getHoraFin(),
                reservaId
        );

        if (!conflictos.isEmpty()) {
            throw new RuntimeException("El horario solicitado ya está reservado.");
        }

        reservaExistente.setFecha(nuevaReserva.getFecha());
        reservaExistente.setHoraInicio(nuevaReserva.getHoraInicio());
        reservaExistente.setHoraFin(nuevaReserva.getHoraFin());
        reservaExistente.setCanchaId(nuevaReserva.getCanchaId());

        ReservaEntity actualizada = reservaRepository.save(reservaExistente);
        return reservaMapper.entityToDto(actualizada);
    }

    @Override
    public void cancelarReserva(Long reservaId) {
        ReservaEntity reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getFecha().isBefore(LocalDate.now()) || !reserva.isActiva()) {
            throw new RuntimeException("Solo se pueden cancelar reservas futuras activas");
        }

        reserva.setActiva(false);
        reservaRepository.save(reserva);
    }

}
