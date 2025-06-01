package co.gestion.canchas.reservas.autenticacion.utils.mappers;

import co.gestion.canchas.reservas.autenticacion.dto.UserDTO;
import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Interfaz que define los métodos de mapeo entre las entidades {@link UserEntity} y los DTOs {@link UserDTO}.
 * <p>
 * Utiliza MapStruct para generar automáticamente las implementaciones de los métodos de mapeo.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte una entidad {@link UserEntity} a un objeto DTO {@link UserDTO}.
     *
     * @param entity la entidad {@link UserDTO} a convertir.
     * @return el objeto {@link UserDTO} correspondiente.
     */
    UserDTO entityToDto(UserEntity entity);

    /**
     * Convierte un objeto DTO {@link UserDTO} a una entidad {@link UserEntity}.
     *
     * @param dto el objeto {@link UserDTO} a convertir.
     * @return la entidad {@link UserEntity} correspondiente.
     */
    UserEntity dtoToEntity(UserDTO dto);

    /**
     * Convierte una lista de entidades {@link UserEntity} a una lista de objetos DTO {@link UserDTO}.
     *
     * @param entities la lista de entidades {@link UserEntity} a convertir.
     * @return la lista de objetos {@link UserDTO} correspondiente.
     */
    List<UserDTO> listEntityToListDto(List<UserEntity> entities);

    /**
     * Convierte una lista de objetos DTO {@link UserDTO} a una lista de entidades {@link UserEntity}.
     *
     * @param dtos la lista de objetos {@link UserDTO} a convertir.
     * @return la lista de entidades {@link UserEntity} correspondiente.
     */
    List<UserEntity> listDtoToListEntity(List<UserDTO> dtos);
}
