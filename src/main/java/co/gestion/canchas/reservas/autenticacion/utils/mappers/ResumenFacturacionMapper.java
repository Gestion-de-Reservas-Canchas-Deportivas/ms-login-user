package co.gestion.canchas.reservas.autenticacion.utils.mappers;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResumenFacturacionMapper {

    public FacturacionDTO toDto(ReservaEntity reserva, String tipoCancha, BigDecimal precioPorHora) {
        FacturacionDTO dto = new FacturacionDTO();
        dto.setReservaId(reserva.getId());
        dto.setUsuarioId(reserva.getUsuarioId());
        dto.setTipoCancha(tipoCancha);
        dto.setFecha(reserva.getFecha());
        dto.setHoraInicio(reserva.getHoraInicio().toString());
        dto.setHoraFin(reserva.getHoraFin().toString());

        long minutos = Duration.between(reserva.getHoraInicio(), reserva.getHoraFin()).toMinutes();
        BigDecimal horas = BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        dto.setMonto(precioPorHora.multiply(horas));

        return dto;
    }

    public List<FacturacionDTO> listEntityToListDto(List<ReservaEntity> entities, String tipoCancha, BigDecimal precioPorHora) {
        List<FacturacionDTO> result = new ArrayList<>();
        for (ReservaEntity reserva : entities) {
            result.add(toDto(reserva, tipoCancha, precioPorHora));
        }
        return result;
    }
}
