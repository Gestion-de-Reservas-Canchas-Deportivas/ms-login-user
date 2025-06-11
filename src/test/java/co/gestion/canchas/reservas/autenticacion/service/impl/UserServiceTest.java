package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import co.gestion.canchas.reservas.autenticacion.repository.UserRepository;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void fetchUserByIdExitoso() {
        Long id = 1L;
        UserEntity entity = new UserEntity();
        UserDTO dto = new UserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        doReturn(dto).when(userMapper).entityToDto(entity);

        UserDTO result = userService.fetchUserById(id);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(id);
        verify(userMapper, times(1)).entityToDto(entity);
    }

    @Test
    void fetchAllUserFallido() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Error simulado"));
        assertThrows(RuntimeException.class, () -> userService.fetchAllUser());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void fetchUserByIdFallido() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> userService.fetchUserById(id));
        assertEquals("User not found with ID: " + id, ex.getMessage());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void deleteUserExitoso() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        boolean deleted = userService.deleteUser(id);

        assertTrue(deleted);
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUserFallido() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(id));
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateUserExitoso() {
        Long id = 1L;
        UserEntity entity = new UserEntity();
        UserDTO dto = new UserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRepository.save(entity)).thenReturn(entity);
        doReturn(dto).when(userMapper).entityToDto(entity);

        UserDTO updated = userService.updateUser(id, dto);

        assertNotNull(updated);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(entity);
        verify(userMapper, times(1)).entityToDto(entity);
    }

    @Test
    void updateUserFallido() {
        Long id = 1L;
        UserDTO dto = new UserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(id, dto));
        assertEquals("User not found with ID: " + id, ex.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}