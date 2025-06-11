package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import co.gestion.canchas.reservas.autenticacion.repository.UserRepository;
import co.gestion.canchas.reservas.autenticacion.service.IUserService;
import co.gestion.canchas.reservas.autenticacion.utils.exceptions.ResourceNotFoundException;
import co.gestion.canchas.reservas.autenticacion.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDTO saveUser(UserDTO user) {
        UserEntity entity = userMapper.dtoToEntity(user);

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            entity.setHashPassword(hashedPassword);
        } else {
            throw new RuntimeException("Password is required to create a user");
        }

        UserEntity savedEntity = userRepository.save(entity);
        UserDTO savedDTO = userMapper.entityToDto(savedEntity);
        savedDTO.setHashPassword(savedEntity.getHashPassword());
        return savedDTO;
    }

    @Override
    public List<UserDTO> fetchAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO fetchUserById(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        return userOpt.map(userMapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }


    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO user) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            UserEntity existingUser = userOpt.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmailAddress(user.getEmailAddress());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setBirthDate(user.getBirthDate());

            UserEntity updatedEntity = userRepository.save(existingUser);
            return userMapper.entityToDto(updatedEntity);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }
}
