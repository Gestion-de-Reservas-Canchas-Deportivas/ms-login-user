package co.gestion.canchas.reservas.autenticacion.service;


import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;

import java.util.List;

public interface HorarioService {

    HorarioDTO create(HorarioDTO dto);

    List<HorarioDTO> findByCancha(Long canchaId);

    HorarioDTO update(Long id, HorarioDTO dto);

    void delete(Long id);

    List<HorarioDTO> findAll();

}
