package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.FacturacionDTO;

import java.time.LocalDate;
import java.util.List;

public interface FacturacionService {

    List<FacturacionDTO> obtenerResumen(LocalDate inicio, LocalDate fin, Long tipoCanchaId, Long usuarioId);

}