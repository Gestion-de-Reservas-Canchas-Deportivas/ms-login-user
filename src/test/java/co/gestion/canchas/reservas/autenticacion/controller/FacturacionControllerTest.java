package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;
import co.gestion.canchas.reservas.autenticacion.service.FacturacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturacionControllerTest {

    @InjectMocks
    private FacturacionController facturacionController;

    @Mock
    private FacturacionService facturacionService;

    @Test
    void testObtenerResumenFacturacion() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 15);
        LocalDate fin = LocalDate.of(2025, 6, 17);
        Long tipoCanchaId = 1L;
        Long usuarioId = 2L;

        FacturacionDTO dto1 = new FacturacionDTO(4L, 2L, "Fútbol", "2025-06-15", "14:00", "16:00", new BigDecimal("20000"));
        FacturacionDTO dto2 = new FacturacionDTO(5L, 2L, "Fútbol", "2025-06-16", "10:00", "12:00", new BigDecimal("30000"));

        List<FacturacionDTO> resumen = Arrays.asList(dto1, dto2);

        when(facturacionService.obtenerResumen(inicio, fin, tipoCanchaId, usuarioId)).thenReturn(resumen);

        // Act
        ResponseEntity<?> response = facturacionController.obtenerResumenFacturacion(inicio, fin, tipoCanchaId, usuarioId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof FacturacionController.FacturacionResponse);

        FacturacionController.FacturacionResponse body = (FacturacionController.FacturacionResponse) response.getBody();
        assertEquals(2, body.resumen().size());
        assertEquals(new BigDecimal("50000"), body.totalFacturado());

        verify(facturacionService, times(1)).obtenerResumen(inicio, fin, tipoCanchaId, usuarioId);
    }
}
