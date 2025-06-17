package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.TipoCanchaDTO;
import co.gestion.canchas.reservas.autenticacion.service.ITipoCanchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoCanchaControllerTest {

    @InjectMocks
    private TipoCanchaController controller;

    @Mock
    private ITipoCanchaService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        TipoCanchaDTO dto = new TipoCanchaDTO();
        when(service.createTipoCancha(dto)).thenReturn(dto);

        ResponseEntity<TipoCanchaDTO> response = controller.create(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(service).createTipoCancha(dto);
    }

    @Test
    void testGetAll() {
        List<TipoCanchaDTO> lista = List.of(new TipoCanchaDTO(), new TipoCanchaDTO());
        when(service.fetchAllTipos()).thenReturn(lista);

        ResponseEntity<List<TipoCanchaDTO>> response = controller.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(service).fetchAllTipos();
    }

    @Test
    void testGetById() {
        Long id = 1L;
        TipoCanchaDTO dto = new TipoCanchaDTO();
        when(service.fetchTipoById(id)).thenReturn(dto);

        ResponseEntity<TipoCanchaDTO> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(service).fetchTipoById(id);
    }

    @Test
    void testUpdate() {
        Long id = 2L;
        TipoCanchaDTO dto = new TipoCanchaDTO();
        when(service.updateTipoCancha(id, dto)).thenReturn(dto);

        ResponseEntity<TipoCanchaDTO> response = controller.update(id, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(service).updateTipoCancha(id, dto);
    }

    @Test
    void testDelete() {
        Long id = 3L;
        when(service.deleteTipoCancha(id)).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> response = controller.delete(id);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().get("deleted"));
        verify(service).deleteTipoCancha(id);
    }
}
