package co.gestion.canchas.reservas.autenticacion.service;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO saveUser(UserDTO user);

    List<UserDTO> fetchAllUser();

    UserDTO fetchUserById(Long id);

    boolean deleteUser(Long id);

    UserDTO updateUser(Long id, UserDTO user);
}
