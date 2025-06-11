package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;
import co.gestion.canchas.reservas.autenticacion.service.ILoginUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private ILoginUserService iLoginUserService;

    @Test
    void loginUserExitoso() {
        // Preparación de datos
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmailAddress("usuario@ejemplo.com");
        loginUserDTO.setPassword("12345");

        RespuestaGeneralDTO respuestaEsperada = new RespuestaGeneralDTO();
        respuestaEsperada.setStatus(HttpStatus.OK);

        // Simular comportamiento del servicio
        when(iLoginUserService.loginUser(any(LoginUserDTO.class)))
                .thenReturn(respuestaEsperada);

        // Ejecución del controlador
        ResponseEntity<RespuestaGeneralDTO> response = loginController.loginUser(loginUserDTO);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(respuestaEsperada, response.getBody());

        // Verificar interacción con el servicio
        verify(iLoginUserService, times(1)).loginUser(any(LoginUserDTO.class));
    }
}
