package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.AuthResponseDTO;
import co.gestion.canchas.reservas.autenticacion.dto.LoginUserDTO;
import co.gestion.canchas.reservas.autenticacion.dto.RespuestaGeneralDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

    @InjectMocks
    private LoginUserService loginUserService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    private static final String INICIO_EXITOSO = "Inicio de sesión exitoso";
    private static final String ERROR_CREDENCIALES = "Credenciales incorrectas";

    @Test
    void loginExitoso() {
        // Arrange
        String email = "usuario@test.com";
        String password = "password123";
        String token = "mocked-jwt-token";

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmailAddress(email);
        loginUserDTO.setPassword(password);

        Authentication authMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);
        when(authMock.isAuthenticated()).thenReturn(true);
        when(authMock.getPrincipal()).thenReturn(userDetailsMock);
        when(jwtService.getToken(userDetailsMock)).thenReturn(token);

        // Act
        RespuestaGeneralDTO respuesta = loginUserService.loginUser(loginUserDTO);

        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatus());
        assertEquals(INICIO_EXITOSO, respuesta.getMessage());
        assertNotNull(respuesta.getData());
        AuthResponseDTO data = (AuthResponseDTO) respuesta.getData();
        assertEquals(token, data.getToken());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).getToken(userDetailsMock);
    }

    @Test
    void loginFallido() {
        // Arrange
        String email = "usuario@test.com";
        String password = "wrongPassword";

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmailAddress(email);
        loginUserDTO.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Auth failed"));

        // Act
        RespuestaGeneralDTO respuesta = loginUserService.loginUser(loginUserDTO);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatus());
        assertEquals(ERROR_CREDENCIALES, respuesta.getMessage());
        assertNull(respuesta.getData());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }
}