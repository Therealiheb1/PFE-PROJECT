package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_id", nullable = false, unique = true)
    private String keyId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cin", unique = true, nullable = false)
    private String cin;

    @Column(name = "sexe", nullable = false)
    private String sexe;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "agence", nullable = false)
    private String agence;

    @Column(name = "profession", nullable = false)
    private String profession;

    @Column(name = "daten", nullable = false)
    private LocalDate DateN;
}
