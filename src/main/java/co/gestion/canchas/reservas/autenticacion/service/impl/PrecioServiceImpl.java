package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.PrecioEntity;
import co.gestion.canchas.reservas.autenticacion.repository.PrecioRepository;
import co.gestion.canchas.reservas.autenticacion.service.PrecioService;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.PrecioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrecioServiceImpl implements PrecioService {

    private final PrecioRepository repo;
    private final PrecioMapper mapper;

    private void validarPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ingrese un valor válido");
        }
    }

    @Override
    public PrecioDTO create(PrecioDTO dto) {
        validarPrecio(dto.getPrecio());
        PrecioEntity ent = mapper.dtoToEntity(dto);
        PrecioEntity saved = repo.save(ent);
        return mapper.entityToDto(saved);
    }

    @Override
    public List<PrecioDTO> findByCancha(Long canchaId) {
        return repo.findByCanchaId(canchaId)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrecioDTO> findByHorario(Long horarioId) {
        return repo.findByHorarioId(horarioId)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PrecioDTO update(Long id, PrecioDTO dto) {
        PrecioEntity existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio no encontrado: " + id));
        validarPrecio(dto.getPrecio());
        existing.setPrecio(dto.getPrecio());
        existing.setCanchaId(dto.getCanchaId());
        existing.setHorarioId(dto.getHorarioId());
        PrecioEntity upd = repo.save(existing);
        return mapper.entityToDto(upd);
    }

    @Override
    public List<PrecioDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }


    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Precio no encontrado: " + id);
        }
        repo.deleteById(id);
    }
}
