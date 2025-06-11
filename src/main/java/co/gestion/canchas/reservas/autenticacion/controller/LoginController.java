package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;
import co.gestion.canchas.reservas.autenticacion.service.ILoginUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class LoginController {

    /**
     * Servicio para la lógica de inicio de sesión de usuarios.
     */
    private final ILoginUserService iLoginUserService;

    @PostMapping("/login")
    public ResponseEntity<RespuestaGeneralDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = iLoginUserService.loginUser(loginUserDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<RespuestaGeneralDTO> forgotPassword(@RequestBody Map<String,String> body) {
        String email = body.get("emailAddress");
        RespuestaGeneralDTO resp = iLoginUserService.recoverPassword(email);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}
