package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CustomersTable")
public class User{
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long Id;
    @Column(name = "np")
    private String nom_prenom;

    @Column(name = "email")
    private String email;
    @Column(name = "cin")
    private Integer cin;
    @Column(name = "tel")
    private Integer telphone;
    @Column(name = "age")
    private Integer age;

    @Column(name = "sexe")
    private String sexe;
    @Column(name = "agence")
    private Integer agence;
    
}
