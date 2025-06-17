package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.service.HorarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioGlobalControllerTest {

    @InjectMocks
    private HorarioGlobalController controller;

    @Mock
    private HorarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllHorarios() {
        HorarioDTO h1 = new HorarioDTO();
        HorarioDTO h2 = new HorarioDTO();
        List<HorarioDTO> horarios = List.of(h1, h2);

        when(service.findAll()).thenReturn(horarios);

        ResponseEntity<List<HorarioDTO>> response = controller.listAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAll();
    }
}
