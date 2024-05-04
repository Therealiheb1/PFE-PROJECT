package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;

import lombok.*;


@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compte", uniqueConstraints = @UniqueConstraint(columnNames = {"iban"}))
public class Cbank {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rib", nullable = false, updatable = false)
    private Long rib;
    @Column(name = "iban", unique = true)
    private String iban;
    @Column(name = "num_compte")
    private Integer num_compte;
    @Column(name = "client")
    private Integer client;
    @Column(name = "solde")
    private Double solde;
    @Column(name = "statue")
    private Boolean statue;

}
