package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.HorarioEntity;
import co.gestion.canchas.reservas.autenticacion.repository.HorarioRepository;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.HorarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioServiceImplTest {

    @InjectMocks
    private HorarioServiceImpl horarioService;

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private HorarioMapper horarioMapper;


    @Test
    void updateHorarioNoExistenteLanzaExcepcion() {
        Long id = 100L;
        HorarioDTO dto = new HorarioDTO();

        when(horarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> horarioService.update(id, dto));
    }

    @Test
    void deleteHorarioNoExistenteLanzaExcepcion() {
        Long id = 200L;

        when(horarioRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> horarioService.delete(id));
    }

    @Test
    void findByCanchaDebeRetornarListaDTOs() {
        Long canchaId = 1L;
        HorarioEntity entity = new HorarioEntity();
        HorarioDTO dto = new HorarioDTO();

        when(horarioRepository.findByCanchaId(canchaId)).thenReturn(List.of(entity));
        when(horarioMapper.entityToDto(entity)).thenReturn(dto);

        List<HorarioDTO> result = horarioService.findByCancha(canchaId);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void findAllDebeRetornarListaDTOs() {
        HorarioEntity entity = new HorarioEntity();
        HorarioDTO dto = new HorarioDTO();

        when(horarioRepository.findAll()).thenReturn(List.of(entity));
        when(horarioMapper.entityToDto(entity)).thenReturn(dto);

        List<HorarioDTO> result = horarioService.findAll();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
