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

class ObtenerPrecioAllTest {

    @InjectMocks
    private ObtenerPrecioAll controller;

    @Mock
    private PrecioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllPrecios() {
        PrecioDTO p1 = new PrecioDTO();
        PrecioDTO p2 = new PrecioDTO();
        List<PrecioDTO> precios = List.of(p1, p2);

        when(service.findAll()).thenReturn(precios);

        ResponseEntity<List<PrecioDTO>> response = controller.listAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAll();
    }
}
