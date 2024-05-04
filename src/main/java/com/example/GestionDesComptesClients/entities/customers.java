package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers_table", uniqueConstraints = @UniqueConstraint(columnNames = {"cin","email"}))
public class customers {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long Id;
    @Column(name = "customer_First_Name")
    private String first_name;
    @Column(name = "customer_last_name")
    private String last_name;
    @Column(name = "cin", unique = true)
    private String cin;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "tel")
    private String tel;
    @Column(name = "email")
    private String email;
    @Column(name = "agence")
    private String agence;
    @Column(name = "profession")
    private String profession;
    @Column(name = "daten")
    private LocalDate daten;
}

