package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.service.PrecioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrecioControllerTest {

    @InjectMocks
    private PrecioController controller;

    @Mock
    private PrecioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Long canchaId = 1L;
        Long horarioId = 2L;
        PrecioDTO dto = new PrecioDTO();
        PrecioDTO created = new PrecioDTO();

        when(service.create(dto)).thenReturn(created);

        ResponseEntity<PrecioDTO> response = controller.create(canchaId, horarioId, dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(created, response.getBody());
        assertEquals(canchaId, dto.getCanchaId());
        assertEquals(horarioId, dto.getHorarioId());
    }

    @Test
    void testListByCancha() {
        Long canchaId = 1L;
        List<PrecioDTO> precios = List.of(new PrecioDTO(), new PrecioDTO());

        when(service.findByCancha(canchaId)).thenReturn(precios);

        ResponseEntity<List<PrecioDTO>> response = controller.listByCancha(canchaId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(service).findByCancha(canchaId);
    }

    @Test
    void testListByHorario() {
        Long horarioId = 2L;
        List<PrecioDTO> precios = List.of(new PrecioDTO());

        when(service.findByHorario(horarioId)).thenReturn(precios);

        ResponseEntity<List<PrecioDTO>> response = controller.listByHorario(horarioId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(service).findByHorario(horarioId);
    }

    @Test
    void testUpdate() {
        Long canchaId = 1L;
        Long horarioId = 2L;
        Long id = 3L;
        PrecioDTO dto = new PrecioDTO();
        PrecioDTO updated = new PrecioDTO();

        when(service.update(id, dto)).thenReturn(updated);

        ResponseEntity<PrecioDTO> response = controller.update(canchaId, horarioId, id, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
        assertEquals(canchaId, dto.getCanchaId());
        assertEquals(horarioId, dto.getHorarioId());
    }

    @Test
    void testDelete() {
        Long id = 5L;

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(service).delete(id);
    }
}
