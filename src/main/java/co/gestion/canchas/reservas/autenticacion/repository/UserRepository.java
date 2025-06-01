package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de acceso a datos de la entidad {@link UserEntity}.
 * Proporciona métodos estándar de CRUD a través de {@link JpaRepository}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param emailAddress el correo electrónico del usuario a buscar.
     * @return un {@link Optional} que contiene el usuario si se encuentra.
     */
    Optional<UserEntity> findByEmailAddress(String emailAddress);
}
