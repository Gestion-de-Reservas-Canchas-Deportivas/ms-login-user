package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import co.gestion.canchas.reservas.autenticacion.repository.ReservaReporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteReservaServiceImplTest {

    @Mock
    private ReservaReporteRepository reservaReporteRepository;

    @InjectMocks
    private ReporteReservaServiceImpl reporteReservaService;


    @Test
    void generarReporte_conListaVacia() {
        // Arrange
        when(reservaReporteRepository.generarReporteNativo(any(), any(), any())).thenReturn(List.of());

        // Act
        List<ReporteReservaDTO> resultado = reporteReservaService.generarReporte(LocalDate.now(), LocalDate.now(), null);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
} 
