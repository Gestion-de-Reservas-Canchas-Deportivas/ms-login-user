package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;

import java.util.List;

public interface PrecioService {

    PrecioDTO create(PrecioDTO dto);

    List<PrecioDTO> findByCancha(Long canchaId);

    List<PrecioDTO> findByHorario(Long horarioId);

    PrecioDTO update(Long id, PrecioDTO dto);

    void delete(Long id);

    List<PrecioDTO> findAll();

}
