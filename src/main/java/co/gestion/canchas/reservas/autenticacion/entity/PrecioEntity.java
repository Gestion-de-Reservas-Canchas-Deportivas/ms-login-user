package co.gestion.canchas.reservas.autenticacion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "precios")
@Data
public class PrecioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK a tipo-cancha
    @Column(name = "cancha_id", nullable = false)
    private Long canchaId;

    // FK a horarios.id
    @Column(name = "horario_id", nullable = false)
    private Long horarioId;

    // Precio para esa franja
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}
