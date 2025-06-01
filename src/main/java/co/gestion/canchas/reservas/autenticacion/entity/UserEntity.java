package co.gestion.canchas.reservas.autenticacion.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entidad que representa la tabla "user" en la base de datos.
 * <p>
 * Almacena la información de los usuarios, incluyendo sus datos personales
 * </p>
 */

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "hash_password", nullable = false, length = 100)
    private String hashPassword;
}
