package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.service.HorarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioControllerTest {

    @InjectMocks
    private HorarioController controller;

    @Mock
    private HorarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHorario() {
        Long canchaId = 1L;
        HorarioDTO dto = new HorarioDTO();
        dto.setHoraInicio(LocalTime.parse("08:00"));
        dto.setHoraFin(LocalTime.parse("09:00"));

        HorarioDTO saved = new HorarioDTO();
        saved.setId(10L);
        saved.setCanchaId(canchaId);
        saved.setHoraInicio(LocalTime.parse("08:00"));
        saved.setHoraFin(LocalTime.parse("09:00"));

        when(service.create(any(HorarioDTO.class))).thenReturn(saved);

        ResponseEntity<HorarioDTO> response = controller.create(canchaId, dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
        assertEquals(canchaId, response.getBody().getCanchaId());
        verify(service).create(dto);
    }

    @Test
    void testListHorarios() {
        Long canchaId = 2L;

        HorarioDTO h1 = new HorarioDTO();
        HorarioDTO h2 = new HorarioDTO();
        List<HorarioDTO> list = List.of(h1, h2);

        when(service.findByCancha(canchaId)).thenReturn(list);

        ResponseEntity<List<HorarioDTO>> response = controller.list(canchaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(service).findByCancha(canchaId);
    }

    @Test
    void testUpdateHorario() {
        Long canchaId = 3L;
        Long id = 5L;
        HorarioDTO dto = new HorarioDTO();
        dto.setHoraInicio(LocalTime.parse("10:00"));
        dto.setHoraFin(LocalTime.parse("11:00"));

        HorarioDTO updated = new HorarioDTO();
        updated.setId(id);
        updated.setCanchaId(canchaId);
        updated.setHoraInicio(LocalTime.parse("10:00"));
        updated.setHoraFin(LocalTime.parse("11:00"));

        when(service.update(eq(id), any(HorarioDTO.class))).thenReturn(updated);

        ResponseEntity<HorarioDTO> response = controller.update(canchaId, id, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
        verify(service).update(eq(id), eq(dto));
    }

    @Test
    void testDeleteHorario() {
        Long canchaId = 4L;
        Long id = 7L;

        doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(canchaId, id);

        assertEquals(204, response.getStatusCodeValue());
        verify(service).delete(id);
    }
}
