package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.CorreoDto;
import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;
import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaRepository;
import co.gestion.canchas.reservas.autenticacion.repository.UserRepository;
import co.gestion.canchas.reservas.autenticacion.service.IEnviarCorreoService;
import co.gestion.canchas.reservas.autenticacion.service.IUserService;
import co.gestion.canchas.reservas.autenticacion.service.ReservaService;
import co.gestion.canchas.reservas.autenticacion.utils.helper.Utils;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.ReservaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static co.gestion.canchas.reservas.autenticacion.utils.constants.Constantes.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final IEnviarCorreoService iEnviarCorreoService;
    private final IUserService iUserService;

    @Value("${spring.mail.username}")
    private String fromEmail;

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
            UserDTO user = iUserService.fetchUserById(reservaDTO.getUsuarioId());
            iEnviarCorreoService.enviarCorreo(getCorreoDto(user, guardada));
            return reservaMapper.entityToDto(guardada);
        } catch (Exception e) {
            log.error("Error al crear la reserva: {}", e.getMessage(), e);
            throw e; // o lanzar una excepción personalizada
        }

    }

    private CorreoDto getCorreoDto(UserDTO user, ReservaEntity guardada) {
        // Validaciones básicas
        if (user == null || guardada == null) {
            throw new IllegalArgumentException("UserDTO y ReservaEntity no pueden ser nulos");
        }
        if (user.getEmailAddress() == null || user.getEmailAddress().isBlank()) {
            throw new IllegalArgumentException("El usuario debe tener un email válido");
        }

        // Formateo de nombre, fecha y hora
        String nombre = Utils.cleanString(user.getFirstName())
                + " "
                + Utils.cleanString(user.getLastName());
        String fecha = guardada.getFecha().format(DF);
        String hora  = guardada.getHoraInicio().format(TF)
                + " - "
                + guardada.getHoraFin().format(TF);
        String cancha = guardada.getCanchaId().toString();

        // Construcción del cuerpo del mensaje
        String texto = String.format(
                PLANTILLA_MENSAJE,
                nombre,
                guardada.getId(),
                fecha,
                hora,
                cancha,
                fromEmail
        );

        // Construcción del DTO con builder
        return CorreoDto.builder()
                .to(user.getEmailAddress())
                .subject("Confirmación de reserva realizada con éxito")
                .text(texto)
                .build();
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
