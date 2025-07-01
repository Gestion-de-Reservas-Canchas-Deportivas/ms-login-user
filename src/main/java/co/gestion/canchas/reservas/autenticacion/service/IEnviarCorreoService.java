package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.CorreoDto;

public interface IEnviarCorreoService {

    void enviarCorreo(CorreoDto correo);

}
