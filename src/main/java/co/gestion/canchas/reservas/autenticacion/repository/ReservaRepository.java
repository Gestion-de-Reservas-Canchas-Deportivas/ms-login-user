package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    List<ReservaEntity> findByUsuarioIdAndActivaTrue(Long usuarioId);

    @Query("""
                SELECT r FROM ReservaEntity r
                WHERE r.canchaId = :canchaId AND r.fecha = :fecha
                AND ((:horaInicio BETWEEN r.horaInicio AND r.horaFin)
                  OR (:horaFin BETWEEN r.horaInicio AND r.horaFin))
                AND r.id <> :reservaId
            """)
    List<ReservaEntity> findConflictos(Long canchaId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Long reservaId);

}