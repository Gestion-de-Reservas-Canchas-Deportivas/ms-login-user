package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaControllerTest {

    @InjectMocks
    private ReservaController controller;

    @Mock
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearReserva() {
        ReservaDTO dto = new ReservaDTO();
        ReservaDTO creada = new ReservaDTO();
        when(reservaService.crearReserva(dto)).thenReturn(creada);

        ResponseEntity<ReservaDTO> response = controller.crearReserva(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(creada, response.getBody());
        verify(reservaService).crearReserva(dto);
    }

    @Test
    void testObtenerActivas() {
        Long usuarioId = 1L;
        List<ReservaDTO> reservas = List.of(new ReservaDTO(), new ReservaDTO());
        when(reservaService.obtenerReservasActivasPorUsuario(usuarioId)).thenReturn(reservas);

        List<ReservaDTO> result = controller.obtenerActivas(usuarioId);

        assertEquals(2, result.size());
        verify(reservaService).obtenerReservasActivasPorUsuario(usuarioId);
    }

    @Test
    void testModificarReserva() {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(10L);

        ResponseEntity<Map<String, String>> response = controller.modificarReserva(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reserva actualizada con éxito", response.getBody().get("mensaje"));
        verify(reservaService).modificarReserva(dto.getId(), dto);
    }

    @Test
    void testCancelarReserva() {
        Long id = 5L;

        ResponseEntity<Map<String, String>> response = controller.cancelarReserva(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reserva cancelada con éxito", response.getBody().get("mensaje"));
        verify(reservaService).cancelarReserva(id);
    }
}
