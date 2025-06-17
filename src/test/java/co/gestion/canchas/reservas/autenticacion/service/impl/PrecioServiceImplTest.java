package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.PrecioEntity;
import co.gestion.canchas.reservas.autenticacion.repository.PrecioRepository;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.PrecioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrecioServiceImplTest {

    @InjectMocks
    private PrecioServiceImpl service;

    @Mock
    private PrecioRepository repo;

    @Spy
    private PrecioMapper mapper;

    @Test
    void updateFallidoPorIdInexistente() {
        Long id = 99L;
        PrecioDTO dto = new PrecioDTO(id, 2L, 3L, new BigDecimal("100.00"));

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, dto));
    }

    @Test
    void deleteExitoso() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }

    @Test
    void deleteFallido() {
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
        verify(repo, never()).deleteById(id);
    }

    @Test
    void findByCanchaExitoso() {
        when(repo.findByCanchaId(1L)).thenReturn(List.of());
        assertNotNull(service.findByCancha(1L));
        verify(repo).findByCanchaId(1L);
    }

    @Test
    void findByHorarioExitoso() {
        when(repo.findByHorarioId(1L)).thenReturn(List.of());
        assertNotNull(service.findByHorario(1L));
        verify(repo).findByHorarioId(1L);
    }

    @Test
    void findAllExitoso() {
        when(repo.findAll()).thenReturn(List.of());
        assertNotNull(service.findAll());
        verify(repo).findAll();
    }
}