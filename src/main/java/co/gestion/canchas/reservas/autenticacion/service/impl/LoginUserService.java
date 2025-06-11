// src/main/java/co/gestion/canchas/reservas/autenticacion/service/impl/LoginUserService.java
package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.AuthResponseDTO;
import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;
import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import co.gestion.canchas.reservas.autenticacion.repository.UserRepository;
import co.gestion.canchas.reservas.autenticacion.service.ILoginUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginUserService implements ILoginUserService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private static final String INICIO_EXITOSO        = "Inicio de sesión exitoso";
    private static final String ERROR_CREDENCIALES    = "Credenciales incorrectas";
    private static final String ERROR_AUTENTICACION   = "Error en la autenticación";
    private static final String NO_REGISTRADO         = "Email no registrado";
    private static final String INSTRUCCIONES_ENVIADAS= "Se enviaron instrucciones al correo";

    @Override
    public RespuestaGeneralDTO loginUser(LoginUserDTO dto) {
        RespuestaGeneralDTO r = new RespuestaGeneralDTO();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmailAddress(), dto.getPassword())
            );
            if (auth.isAuthenticated()) {
                String token = jwtService.getToken((UserDetails) auth.getPrincipal());
                r.setData(AuthResponseDTO.builder().token(token).build());
                r.setMessage(INICIO_EXITOSO);
                r.setStatus(HttpStatus.OK);
            } else {
                r.setMessage(ERROR_CREDENCIALES);
                r.setStatus(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error(ERROR_AUTENTICACION, ex);
            r.setMessage(ERROR_CREDENCIALES);
            r.setStatus(HttpStatus.UNAUTHORIZED);
        }
        return r;
    }

    @Override
    public RespuestaGeneralDTO recoverPassword(String email) {
        RespuestaGeneralDTO r = new RespuestaGeneralDTO();
        Optional<UserEntity> opt = userRepository.findByEmailAddress(email);
        if (opt.isEmpty()) {
            r.setStatus(HttpStatus.NOT_FOUND);
            r.setMessage(NO_REGISTRADO);
            return r;
        }

        r.setStatus(HttpStatus.OK);
        r.setMessage(INSTRUCCIONES_ENVIADAS);
        return r;
    }

}
