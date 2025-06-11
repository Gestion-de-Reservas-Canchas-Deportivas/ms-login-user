package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO();
        when(userService.saveUser(userDTO)).thenReturn(userDTO);

        UserDTO result = userController.saveUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userService, times(1)).saveUser(userDTO);
    }

    @Test
    void testFetchAllUser() {
        UserDTO user1 = new UserDTO();
        UserDTO user2 = new UserDTO();
        List<UserDTO> userList = Arrays.asList(user1, user2);
        when(userService.fetchAllUser()).thenReturn(userList);

        List<UserDTO> result = userController.fetchAllUser();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userService, times(1)).fetchAllUser();
    }

    @Test
    void testFetchUserById() {
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.fetchUserById(id)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.fetchUsertById(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).fetchUserById(id);
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;
        when(userService.deleteUser(id)).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> response = userController.deleteUser(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().get("User deleted successfully"));
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.updateUser(id, userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(id, userDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).updateUser(id, userDTO);
    }
}