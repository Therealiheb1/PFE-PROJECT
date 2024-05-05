package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "num_compte")
public class num_compte  {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num", nullable = false, updatable = false)
    private Long num;
}
