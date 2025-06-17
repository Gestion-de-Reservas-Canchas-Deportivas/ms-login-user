package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import co.gestion.canchas.reservas.autenticacion.repository.PrecioRepository;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaRepository;
import co.gestion.canchas.reservas.autenticacion.repository.TipoCanchaRepository;
import co.gestion.canchas.reservas.autenticacion.service.FacturacionService;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.ResumenFacturacionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacturacionServiceImpl implements FacturacionService {

    private final ReservaRepository reservaRepository;
    private final PrecioRepository precioRepository;
    private final TipoCanchaRepository tipoCanchaRepository;
    private final ResumenFacturacionMapper mapper;

    @Override
    public List<FacturacionDTO> obtenerResumen(LocalDate inicio, LocalDate fin, Long tipoCanchaId, Long usuarioId) {
        List<ReservaEntity> reservas = reservaRepository.findAll().stream()
                .filter(r -> !r.getFecha().isBefore(inicio) && !r.getFecha().isAfter(fin))
                .filter(r -> tipoCanchaId == null || tipoCanchaRepository.findById(r.getCanchaId()).orElseThrow().getId().equals(tipoCanchaId))
                .filter(r -> usuarioId == null || r.getUsuarioId().equals(usuarioId))
                .toList();

        return reservas.stream()
                .map(reserva -> {
                    BigDecimal precioPorHora = precioRepository.obtenerPrecioPorCanchaYHora(
                            reserva.getCanchaId(),
                            reserva.getHoraInicio(),
                            reserva.getHoraFin()
                    );

                    if (precioPorHora == null) {
                        log.warn("Precio no encontrado para canchaId={} entre {} - {}. Se asigna 0.",
                                reserva.getCanchaId(), reserva.getHoraInicio(), reserva.getHoraFin());
                        precioPorHora = BigDecimal.ZERO;
                    }

                    String tipo = tipoCanchaRepository.findById(reserva.getCanchaId())
                            .orElseThrow()
                            .getName();

                    return mapper.toDto(reserva, tipo, precioPorHora);
                })
                .toList();
    }

}