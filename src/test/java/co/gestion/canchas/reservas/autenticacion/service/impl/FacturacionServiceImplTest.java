package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import co.gestion.canchas.reservas.autenticacion.entity.TipoCanchaEntity;
import co.gestion.canchas.reservas.autenticacion.repository.PrecioRepository;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaRepository;
import co.gestion.canchas.reservas.autenticacion.repository.TipoCanchaRepository;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.ResumenFacturacionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturacionServiceImplTest {

    @InjectMocks
    private FacturacionServiceImpl facturacionService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PrecioRepository precioRepository;

    @Mock
    private TipoCanchaRepository tipoCanchaRepository;

    @Mock
    private ResumenFacturacionMapper mapper;

    @Test
    void obtenerResumen_debeRetornarFacturacionDTOs() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fin = LocalDate.of(2025, 6, 30);
        Long tipoCanchaId = 1L;
        Long usuarioId = 2L;

        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(100L);
        reserva.setCanchaId(tipoCanchaId);
        reserva.setUsuarioId(usuarioId);
        reserva.setFecha(LocalDate.of(2025, 6, 15));
        reserva.setHoraInicio(LocalTime.parse("10:00"));
        reserva.setHoraFin(LocalTime.parse("12:00"));

        TipoCanchaEntity tipo = new TipoCanchaEntity();
        tipo.setId(tipoCanchaId);
        tipo.setName("Fútbol");

        FacturacionDTO dto = new FacturacionDTO();
        dto.setReservaId(100L);
        dto.setMonto(BigDecimal.valueOf(20000));

        when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        when(tipoCanchaRepository.findById(tipoCanchaId)).thenReturn(Optional.of(tipo));
        when(precioRepository.obtenerPrecioPorCanchaYHora(tipoCanchaId, LocalTime.parse("10:00"), LocalTime.parse("12:00")))
                .thenReturn(BigDecimal.valueOf(20000));
        when(mapper.toDto(reserva, "Fútbol", BigDecimal.valueOf(20000))).thenReturn(dto);

        // Act
        List<FacturacionDTO> resultado = facturacionService.obtenerResumen(inicio, fin, tipoCanchaId, usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(20000, resultado.get(0).getMonto().intValue());

        verify(reservaRepository, times(1)).findAll();
        verify(tipoCanchaRepository, atLeastOnce()).findById(tipoCanchaId);
        verify(precioRepository, times(1)).obtenerPrecioPorCanchaYHora(tipoCanchaId, LocalTime.parse("10:00"), LocalTime.parse("12:00"));
        verify(mapper, times(1)).toDto(reserva, "Fútbol", BigDecimal.valueOf(20000));
    }
}
