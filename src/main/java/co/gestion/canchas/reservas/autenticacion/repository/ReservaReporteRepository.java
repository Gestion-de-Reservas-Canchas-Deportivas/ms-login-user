package co.gestion.canchas.reservas.autenticacion.repository;

import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaReporteRepository extends JpaRepository<ReservaEntity, Long> {

    @Query(value = """
    SELECT 
        tc.name AS tipo_cancha,
        COUNT(r.id) AS total_reservas,
        0 AS horas_utilizadas,
        0 AS porcentaje_ocupacion
    FROM reserva r
    JOIN tipo_cancha tc ON r.cancha_id = tc.id
    WHERE r.fecha BETWEEN :inicio AND :fin
      AND (:tipoId IS NULL OR tc.id = :tipoId)
    GROUP BY tc.name
""", nativeQuery = true)
    List<Object[]> generarReporteNativo(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("tipoId") Long tipoId
    );


}
