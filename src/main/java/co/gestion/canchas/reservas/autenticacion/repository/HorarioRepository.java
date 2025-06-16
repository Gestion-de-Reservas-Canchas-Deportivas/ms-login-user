package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {

    List<HorarioEntity> findByCanchaId(Long canchaId);
}
