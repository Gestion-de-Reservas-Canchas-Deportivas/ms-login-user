package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaReporteRepository;
import co.gestion.canchas.reservas.autenticacion.service.ReporteReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteReservaServiceImpl implements ReporteReservaService {

    private final ReservaReporteRepository reservaReporteRepository;

    @Override
    public List<ReporteReservaDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long tipoCanchaId) {
        List<Object[]> resultados = reservaReporteRepository.generarReporteNativo(fechaInicio, fechaFin, tipoCanchaId);

        return resultados.stream().map(row -> new ReporteReservaDTO(
                "-",                                     // nombreCancha (ya no viene)
                (String) row[0],                         // tipoCancha (tc.name)
                ((Number) row[1]).longValue(),           // totalReservas
                ((Number) row[2]).intValue(),            // horasUtilizadas
                ((Number) row[3]).doubleValue()          // porcentajeOcupacion
        )).collect(Collectors.toList());
    }
}