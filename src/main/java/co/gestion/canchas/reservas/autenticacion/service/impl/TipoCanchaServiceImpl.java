package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.TipoCanchaDTO;
import co.gestion.canchas.reservas.autenticacion.entity.TipoCanchaEntity;
import co.gestion.canchas.reservas.autenticacion.repository.TipoCanchaRepository;
import co.gestion.canchas.reservas.autenticacion.service.ITipoCanchaService;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.TipoCanchaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCanchaServiceImpl implements ITipoCanchaService {

    private final TipoCanchaRepository repo;
    private final TipoCanchaMapper mapper;

    @Override
    public TipoCanchaDTO createTipoCancha(TipoCanchaDTO dto) {
        TipoCanchaEntity entity = mapper.dtoToEntity(dto);
        TipoCanchaEntity saved = repo.save(entity);
        return mapper.entityToDto(saved);
    }

    @Override
    public List<TipoCanchaDTO> fetchAllTipos() {
        return mapper.listEntityToDto(repo.findAll());
    }

    @Override
    public TipoCanchaDTO fetchTipoById(Long id) {
        TipoCanchaEntity entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de cancha no encontrado con ID: " + id));
        return mapper.entityToDto(entity);
    }

    @Override
    public TipoCanchaDTO updateTipoCancha(Long id, TipoCanchaDTO dto) {
        TipoCanchaEntity existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de cancha no encontrado con ID: " + id));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setActivo(dto.getActivo()); // 👈🏽 ESTE FALTABA
        TipoCanchaEntity updated = repo.save(existing);
        return mapper.entityToDto(updated);
    }


    @Override
    public boolean deleteTipoCancha(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Tipo de cancha no encontrado con ID: " + id);
    }
}
