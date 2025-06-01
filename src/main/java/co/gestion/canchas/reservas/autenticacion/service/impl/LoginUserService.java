package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.AuthResponseDTO;
import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;
import co.gestion.canchas.reservas.autenticacion.service.ILoginUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginUserService implements ILoginUserService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private static final String INICIO_EXITOSO = "Inicio de sesión exitoso";
    private static final String ERROR_CREDENCIALES = "Credenciales incorrectas";
    private static final String ERROR_AUTENTICACION = "Error en la autenticación";

    @Override
    public RespuestaGeneralDTO loginUser(LoginUserDTO loginUserDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDTO.getEmailAddress(),
                            loginUserDTO.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.getToken((UserDetails) authentication.getPrincipal());

                respuestaGeneralDTO.setData(AuthResponseDTO.builder().token(token).build());
                respuestaGeneralDTO.setMessage(INICIO_EXITOSO);
                respuestaGeneralDTO.setStatus(HttpStatus.OK);
            } else {
                respuestaGeneralDTO.setMessage(ERROR_CREDENCIALES);
                respuestaGeneralDTO.setStatus(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error(ERROR_AUTENTICACION, e);
            respuestaGeneralDTO.setMessage(ERROR_CREDENCIALES);
            respuestaGeneralDTO.setStatus(HttpStatus.UNAUTHORIZED);
        }
        return respuestaGeneralDTO;
    }

}
