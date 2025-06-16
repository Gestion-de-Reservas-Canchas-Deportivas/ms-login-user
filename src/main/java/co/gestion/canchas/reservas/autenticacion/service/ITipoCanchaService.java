package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.TipoCanchaDTO;

import java.util.List;

public interface ITipoCanchaService {

    TipoCanchaDTO createTipoCancha(TipoCanchaDTO dto);

    List<TipoCanchaDTO> fetchAllTipos();

    TipoCanchaDTO fetchTipoById(Long id);

    TipoCanchaDTO updateTipoCancha(Long id, TipoCanchaDTO dto);

    boolean deleteTipoCancha(Long id);
}
