package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.PrecioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public interface PrecioRepository extends JpaRepository<PrecioEntity, Long> {

    List<PrecioEntity> findByCanchaId(Long canchaId);

    List<PrecioEntity> findByHorarioId(Long horarioId);

    @Query("""
                SELECT p.precio FROM PrecioEntity p
                JOIN HorarioEntity h ON p.horarioId = h.id
                WHERE p.canchaId = :canchaId
                AND :horaInicio >= h.horaInicio AND :horaFin <= h.horaFin
            """)
    BigDecimal obtenerPrecioPorCanchaYHora(
            @Param("canchaId") Long canchaId,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );
}
