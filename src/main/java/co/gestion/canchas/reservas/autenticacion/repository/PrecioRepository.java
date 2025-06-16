package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.PrecioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecioRepository extends JpaRepository<PrecioEntity, Long> {

    List<PrecioEntity> findByCanchaId(Long canchaId);

    List<PrecioEntity> findByHorarioId(Long horarioId);
}
