package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.HorarioEntity;
import co.gestion.canchas.reservas.autenticacion.repository.HorarioRepository;
import co.gestion.canchas.reservas.autenticacion.service.HorarioService;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.HorarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository repo;
    private final HorarioMapper mapper;

    @Override
    public HorarioDTO create(HorarioDTO dto) {
        // 1) Recupera todos los bloques de esta cancha
        List<HorarioEntity> usados = repo.findByCanchaId(dto.getCanchaId());

        // 2) Comprueba si ya existe **exactamente** este bloque
        boolean existeExacto = usados.stream().anyMatch(h ->
                h.getDiaSemana() == dto.getDiaSemana() &&
                        h.getHoraInicio().equals(dto.getHoraInicio()) &&
                        h.getHoraFin().equals(dto.getHoraFin())
        );
        if (existeExacto) {
            throw new IllegalArgumentException(
                    "Horario ya definido para ese día y franja exacta"
            );
        }

        // 3) Prevención de solapamiento (como antes)
        boolean solapa = usados.stream().anyMatch(h ->
                h.getDiaSemana() == dto.getDiaSemana() &&
                        !(dto.getHoraFin().isBefore(h.getHoraInicio()) ||
                                dto.getHoraInicio().isAfter(h.getHoraFin()))
        );
        if (solapa) {
            throw new IllegalArgumentException("Franja horaria solapada");
        }

        // 4) Guardar
        HorarioEntity ent = mapper.dtoToEntity(dto);
        HorarioEntity saved = repo.save(ent);
        return mapper.entityToDto(saved);
    }

    @Override
    public List<HorarioDTO> findByCancha(Long canchaId) {
        return repo.findByCanchaId(canchaId)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioDTO update(Long id, HorarioDTO dto) {
        HorarioEntity existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado: " + id));
        existing.setDiaSemana(dto.getDiaSemana());
        existing.setHoraInicio(dto.getHoraInicio());
        existing.setHoraFin(dto.getHoraFin());
        existing.setCanchaId(dto.getCanchaId());
        // TODO: validar solapamientos
        HorarioEntity upd = repo.save(existing);
        return mapper.entityToDto(upd);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Horario no encontrado: " + id);
        }
        repo.deleteById(id);
    }

    public List<HorarioDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::entityToDto)
                .toList();
    }

}
